package com.luck.cloud.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.luck.cloud.utils.LogUtil;
import com.luck.cloud.utils.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by 12457 on 2017/8/29.
 */

public class SuperFileView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private static String TAG = "SuperFileView";
    private TbsReaderView mTbsReaderView;
    private int saveTime = -1;
    private Context context;

    public SuperFileView(Context context) {
        this(context, null, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }


    private OnGetFilePathListener mOnGetFilePathListener;


    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }


    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    public void displayFile(File mFile) {

        if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {
            //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
            String bsReaderTemp = "/storage/emulated/0/TbsReaderTemp";
            File bsReaderTempFile =new File(bsReaderTemp);

            if (!bsReaderTempFile.exists()) {
                LogUtil.d("准备创建/storage/emulated/0/TbsReaderTemp！！");
                boolean mkdir = bsReaderTempFile.mkdir();
                if(!mkdir){
                    LogUtil.e("创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
                }
            }

            //加载文件
            Bundle localBundle = new Bundle();
            LogUtil.d(mFile.toString());
            localBundle.putString("filePath", mFile.toString());

            localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");

            if (this.mTbsReaderView == null)
                this.mTbsReaderView = getTbsReaderView(context);
            boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);
            if (bool) {
                this.mTbsReaderView.openFile(localBundle);
            }else{
                ToastUtil.toastShortCenter("手机暂不支持打开该格式，请您在电脑端打开");

            }
        } else {
            ToastUtil.toastShortCenter("文件加载失败");
        }

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
            LogUtil.d( "paramString---->null");
            return str;
        }
        LogUtil.d( "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            LogUtil.d( "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        LogUtil.d( "paramString.substring(i + 1)------>" + str);
        return str;
    }

    public void show() {
        if(mOnGetFilePathListener!=null){
            mOnGetFilePathListener.onGetFilePath(this);
        }
    }

    /***
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(SuperFileView mSuperFileView2);
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        LogUtil.e("****************************************************" + integer);
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
