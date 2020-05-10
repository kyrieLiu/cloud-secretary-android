package com.luck.cloud.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.io.IOException;

/**
 * Project :  yunaandroid.
 * Package name: com.renwei.yunlong.activity
 * Created by :  Benjamin.
 * Created time: 2017/11/24 10:58
 * Changed by :  Benjamin.
 * Changed time: 2017/11/24 10:58
 * Class description:
 */

public class MyFileDisplayActivity extends Activity implements TbsReaderView.ReaderCallback {
    private final String TAG = "MyFileDisplayActivity";
    TextView tvInformation;
    LinearLayout tbsParent;
    private TbsReaderView mTbsReaderView;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_file_display);
        String path = getIntent().getStringExtra("path") == null ? "" : getIntent().getStringExtra("path");
        tbsParent = findViewById(R.id.tbsParent);
        tvInformation = findViewById(R.id.tv_information);
        tbsParent = findViewById(R.id.tbsParent);
        mTbsReaderView = new TbsReaderView(this, MyFileDisplayActivity.this);
        tbsParent.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        downloadFile(path);
    }

    private void openFile(String absPath) {
        final File file = new File(absPath);
        if (file.exists()) {
            //显示文件
            final Bundle bundle = new Bundle();
            bundle.putString("filePath", absPath);
            bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
            String pathFormat = parseFormat(absPath);
            boolean result = mTbsReaderView.preOpen(pathFormat, false);
            Log.e(TAG, String.valueOf(file.getName()) + "文件格式:" + pathFormat + "预加载结果" + result);
            if (result) {
                tvInformation.setVisibility(View.GONE);

                mTbsReaderView.openFile(bundle);
            } else {
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText("不支持的文件格式");
            }

        } else {
            tvInformation.setVisibility(View.VISIBLE);
            tvInformation.setText("文件不存在");
        }
    }

    //获取文件的格式判断文件是否支持 这里文件是不需要带.的 ******
    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
        tbsParent.removeView(mTbsReaderView);
    }

    private void downloadFile(String url) {
//        File file = new File(url);
//        String fileName = file.getName();
//        String localFileName = getCacheDir().getAbsolutePath() + fileName;
//        File localFile = new File(localFileName);
//        if (localFile.exists()) {
//            openFile(localFile.getAbsolutePath());
//            return;
//        }
//        OkGo.<File>get(url).tag(this).execute(new FileCallback(getCacheDir().getAbsolutePath(), fileName) {
//            @Override
//            public void onSuccess(Response<File> response) {
//                Log.e(TAG, "onSuccess: 下载成功");
//                if (response.body().exists()) {
//                    openFile(response.body().getAbsolutePath());
//                }
//            }
//
//            @Override
//            public void onError(Response<File> response) {
//                Log.e(TAG, "onError:downloadProgress ---" + response.getException().getMsg());
//                Log.e(TAG, "onError: 下载失败");
//            }
//
//            @Override
//            public void downloadProgress(Progress progress) {
//                Log.e(TAG, "onError:downloadProgress ---" + progress.currentSize);
//            }
//        });
        url = "http://pocketbook.document.jingcaiwang.cn/group1/M00/00/6B/rBMBOF0LLdSAGFtHAFXulOG0dBM72.docx";
        downLoadFromNet(url);
    }

    private void downLoadFromNet(final String url) {

        File file = new File(url);
        String fileName = file.getName();
        String localFileName = getCacheDir().getAbsolutePath() + fileName;
        final File localFile = new File(localFileName);
        if (!localFile.exists()) {
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OKHttpManager.getInstance().getDownloadDelegate().downloadAsyn(url, localFile.getPath(), new OKHttpManager.ResultCallback<String>() {
            @Override
            public void onError(int code, String result, String message) {
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(String response) {
                //superFileView.displayFile(fileN);
                Log.e("tag","加载成功"+localFile.getAbsolutePath());
                openFile(localFile.getAbsolutePath());
            }
        }, this);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    public static void openActivity(Context context, String url) {
        Intent intent = new Intent(context, MyFileDisplayActivity.class);
        intent.putExtra("path", url);
        context.startActivity(intent);
    }
}
