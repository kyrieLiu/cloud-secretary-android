package com.luck.cloud.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyin on 2019/01/31
 *
 * Describe UncaughtException处理类, 当程序发生Uncaught异常的时候, 有该类来接管程序, 并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";


    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler instance = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 程序的主Activity的class
    private Class<?> mainActivityClass;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context context, Class<?> activityClass) {
        mContext = context;
        this.setMainActivityClass(activityClass);
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //3秒后重启应用
            try {
                Thread.sleep(3000);
//                Intent intent = new Intent(mContext, GuideActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (InterruptedException e) {
//				Log.e("debug", "error：", e);
            }


        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                // 收集设备错误信息
                collectDeviceInfo(mContext.getApplicationContext(), ex);
                Looper.loop();
            }
        }).start();

        // 保存日志文件
        //logError(errorMessage);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx, Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("程序异常,系统将为您重启应用\n");
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                sb.append("versionName :  " + versionName + "\n");
                sb.append("versionCode :  " + versionCode + "\n");
            }
        } catch (NameNotFoundException e) {
        }

        sb.append("phone manufacturer: " + SystemUtil.getDeviceBrand() + "\n");
        sb.append("phone model: " + SystemUtil.getSystemModel() + "\n");
        sb.append("android version: " + SystemUtil.getSystemVersion() + "\n");

        sb.append("异常信息：" + ex.getLocalizedMessage() + "\n");
        int num = ex.getStackTrace().length;
        for (int i = 0; i < num; i++) {
            sb.append(ex.getStackTrace()[i].toString());
            sb.append("\n");
        }
        final String errorMessage = sb.toString();
        Toast.makeText(mContext.getApplicationContext(),
                errorMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * 保存错误信息到文件中
     *
     * @param errorMessage
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private void logError(String errorMessage) {


        File fileX = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                , "xinyuan");
        if (!fileX.exists()) {
            fileX.mkdirs();
        }
        File file = new File(fileX, System.currentTimeMillis() + ".txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            fos.write(errorMessage.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//		Log.e(TAG, "出现未捕捉异常，程序异常退出！", ex);
    }

    public Class<?> getMainActivityClass() {
        return mainActivityClass;
    }

    public void setMainActivityClass(Class<?> mainActivityClass) {
        this.mainActivityClass = mainActivityClass;
    }
}
