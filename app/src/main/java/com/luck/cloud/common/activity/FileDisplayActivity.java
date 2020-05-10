package com.luck.cloud.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.FileUtil;
import com.luck.cloud.utils.Md5Tool;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.SuperFileView;

import java.io.File;
import java.io.IOException;

/**
 * Created by liuyin on 2019/5/20 13:53
 * Describe: 基于腾讯服务加载PDF,word,excel,text等文件
 */
public class FileDisplayActivity extends BaseActivity {

    private String TAG = "FileDisplayActivity";
    //文件展示UI
    SuperFileView mSuperFileView;

    String filePath;

    public static void start(Context context, String path, String title) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("title", title);
        context.startActivity(intent);

    }

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_file_display;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    @Override
    protected void loadData() {

        init();
    }


    public void init() {

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        //path = "http://pocketbook.document.jingcaiwang.cn/group1/M00/00/6B/rBMBOF0LLdSAGFtHAFXulOG0dBM72.docx";
        mSuperFileView = findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView supersFileView) {
                getFilePathAndShowFile(supersFileView);
            }
        });

        if (!TextUtils.isEmpty(path)) {
            setFilePath(path);
            mSuperFileView.show();
        }

    }


    private void getFilePathAndShowFile(SuperFileView mSuperFileView2) {

        if (getFilePath().contains("http")) {//网络地址要先下载
            downLoadFromNet(getFilePath(), mSuperFileView2);
        } else {
            mSuperFileView2.displayFile(new File(getFilePath()));
        }
    }


    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }

    private String getFilePath() {
        return filePath;
    }

    private void downLoadFromNet(final String url, final SuperFileView superFileView) {

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
                    superFileView.displayFile(fileN);
                }
            }, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取缓存目录
     *
     * @param url
     * @return
     */
    private File getCacheDir(String url) {

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), AppConstants.SD_PATH);

    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getCacheFile(File parentFile, String url) {
        File cacheFile = new File(parentFile, getFileName(url));
        Log.d(TAG, "缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        return str;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
        deleteCacheDir();
    }
}
