package com.luck.cloud.utils;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.FileProvider;


import com.luck.cloud.R;
import com.luck.cloud.common.entity.Define;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.network.OKHttpManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by liuyin on 2019/5/20 16:19
 * Describe: 基于wps预览文件
 */
public class WpsUtil {
    /**
     * 更新回调
     */
    public WpsInterface wpsInterface;
    /**
     * 监听wps操作变化
     */
    private WpsCloseListener wpsCloseListener = null;
    /**
     * 是否支持编辑
     */
    private Boolean canWrite;
    private Context mContext;
    private String fileUrl;
    //等待加载框
    private Dialog loadingDialog;


    public WpsUtil(WpsInterface wpsInterface, Context mContext) {
        this.wpsInterface = wpsInterface;
        this.mContext = mContext;
    }

    /**
     * 先将文件下载下来再打开
     *
     * @param url
     */
    public void openDownFile(String url) {
        Boolean wpsOk = isAvilible(mContext, "cn.wps.moffice_eng");
        if (!wpsOk) {
            ToastUtil.toastShortCenter("请先安装wps office应用");
            return;
        }
        try {
            File parentFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), AppConstants.SD_PATH);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            final File fileN = new File(parentFile, FileUtil.getInstance().getFileName(url));
            if (!fileN.exists()) {
                fileN.createNewFile();
            }
            showRDialog();
            OKHttpManager.getInstance().getDownloadDelegate().downloadAsyn(url, fileN.getPath(), new OKHttpManager.ResultCallback<String>() {
                @Override
                public void onError(int code, String result, String message) {
                    ToastUtil.toastShortCenter(message);
                    hideRDialog();
                }

                @Override
                public void onResponse(String response) {
                    hideRDialog();
                    openDocumentWithFile(fileN, true);
                }
            }, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开本地文件
     *
     * @param file     本地文件路径
     * @param canWrite 是否可编辑
     */
    public void openDocumentWithFile(File file, boolean canWrite) {
        this.canWrite = canWrite;
        openDocument(file);
    }

    /**
     * 打开线上文件
     *
     * @param url      URL路径
     * @param canWrite 是否可编辑
     */
    public void openDocumentWithUrl(String url, boolean canWrite) {
        Boolean wpsOk = isAvilible(mContext, "cn.wps.moffice_eng");
        if (!wpsOk) return;
        this.canWrite = canWrite;
        this.fileUrl = url;
        openDocument(null);
    }

    /**
     * 执行打开文件
     *
     * @param file
     */
    public void openDocument(File file) {
        try {
            wpsCloseListener = new WpsCloseListener();
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.kingsoft.writer.back.key.down");//按下返回键
            filter.addAction("com.kingsoft.writer.home.key.down");//按下home键
            filter.addAction("cn.wps.moffice.file.save");//保存
            filter.addAction("cn.wps.moffice.file.close");//关闭
            mContext.registerReceiver(wpsCloseListener, filter);//注册广播
            if (file != null) {
                openDocWithSimple(file);
            } else {
                openDocFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 打开本地文件
    public void openDocWithSimple(File file) {
        try {
            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("cn.wps.moffice_eng");

            Bundle bundle = new Bundle();
            //打开模式
            bundle.putString(Define.OPEN_MODE, Define.NORMAL);
            if (canWrite) {
                bundle.putString(Define.OPEN_MODE, Define.NORMAL);
                bundle.putBoolean(Define.ENTER_REVISE_MODE, true);//以修订模式打开
            } else {
                bundle.putString(Define.OPEN_MODE, Define.READ_ONLY);
            }
            bundle.putBoolean(Define.SEND_SAVE_BROAD, true);
            bundle.putBoolean(Define.SEND_CLOSE_BROAD, true);
            bundle.putBoolean(Define.HOME_KEY_DOWN, true);
            bundle.putBoolean(Define.BACK_KEY_DOWN, true);
            bundle.putBoolean(Define.ENTER_REVISE_MODE, true);
            bundle.putBoolean(Define.IS_SHOW_VIEW, false);
            bundle.putBoolean(Define.AUTO_JUMP, true);
            bundle.putString(Define.THIRD_PACKAGE, mContext.getPackageName());
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(mContext,
                        mContext.getPackageName() + ".fileProvider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "*/*");
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "*/*");
            }
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开文档
    boolean openDocFile() {
        try {
            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("cn.wps.moffice_eng");
            Bundle bundle = new Bundle();
            if (canWrite) {
                bundle.putString(Define.OPEN_MODE, Define.NORMAL);
                bundle.putBoolean(Define.ENTER_REVISE_MODE, true);//以修订模式打开
            } else {
                bundle.putString(Define.OPEN_MODE, Define.READ_ONLY);
            }
            //打开模式
            bundle.putBoolean(Define.SEND_SAVE_BROAD, true);
            bundle.putBoolean(Define.SEND_CLOSE_BROAD, true);
            bundle.putBoolean(Define.HOME_KEY_DOWN, true);
            bundle.putBoolean(Define.BACK_KEY_DOWN, true);
            bundle.putBoolean(Define.ENTER_REVISE_MODE, true);
            bundle.putBoolean(Define.IS_SHOW_VIEW, false);
            bundle.putBoolean(Define.AUTO_JUMP, true);
            //设置广播
            bundle.putString(Define.THIRD_PACKAGE, mContext.getPackageName());
            //第三方应用的包名，用于对改应用合法性的验证
//            bundle.putBoolean(Define.CLEAR_FILE, true);
            //关闭后删除打开文件
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(fileUrl));
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface WpsInterface {
        void doRequest(String filePath);

        void doFinish();
    }

    // 广播接收器
    private class WpsCloseListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("cn.wps.moffice.file.save")) {
                    String fileSavePath = intent.getExtras().getString(Define.SAVE_PATH);
                    Log.e("tag", "保存后的路径==" + fileSavePath);
                    if (canWrite) {
                        wpsInterface.doRequest(fileSavePath);
                    }
                } else if (intent.getAction().equals("cn.wps.moffice.file.close") ||
                        intent.getAction().equals("com.kingsoft.writer.back.key.down")) {
                    wpsInterface.doFinish();
                    mContext.unregisterReceiver(wpsCloseListener);//注销广播
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void appBack() {
        //获取ActivityManager
        ActivityManager mAm = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        //获得当前运行的task
        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
        //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            if (rti.topActivity.getPackageName().equals(mContext.getPackageName())) {
                Intent LaunchIntent = new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(mContext.getPackageName(), rti.topActivity.getClassName());
                LaunchIntent.setComponent(cn);
                mContext.startActivity(LaunchIntent);
                break;
            }
        }

    }

    /**
     * 查询是否已经安装WPS应用
     *
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        String tag = "isAvilible";
        final PackageManager packageManager = context.getPackageManager(); // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            Log.i(tag, "i=" + i + ", " + pinfo.get(i).packageName);
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName)) return true;
        }
        return false;
    }

    /**
     * 显示加载框
     */
    public void showRDialog() {

        if (loadingDialog == null) {
            loadingDialog = new Dialog(mContext, R.style.custom_dialog_style);
            View dialogView = View.inflate(mContext, R.layout.common_waiting_dialog, (ViewGroup) null);
            loadingDialog.setContentView(dialogView);
            loadingDialog.setCanceledOnTouchOutside(false);//点击空白是否消失
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public void hideRDialog() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
        loadingDialog = null;
    }


}
