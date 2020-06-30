package com.luck.cloud.common.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.luck.cloud.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureInterActivity extends AppCompatActivity {
    private WebView webView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView= (WebView) findViewById(R.id.wv_web);
        WebSettings webSettings=webView.getSettings();
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //用于js调用Android
        webSettings.setJavaScriptEnabled(true);
        //设置编码方式
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new chromClient());
        //访问Android assets文件夹内的
        String url="file:///android_asset/test.html";
        //url="http://10.17.1.90:8848/htmlDemo/file2.html";
        //访问网页Html
        webView.loadUrl(url);

//        Button button=findViewById(R.id.button);
//                button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (Build.VERSION.SDK_INT >= 23) {
//                    int REQUEST_CODE_CONTACT = 101;
//                    String[] permissions = {
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                    //验证是否许可权限
//                    for (String str : permissions) {
//                        if (PictureInterActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                            //申请权限
//                            PictureInterActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                            return;
//                        } else {
//                            //这里就是权限打开之后自己要操作的逻辑
//                            String path= "file://"+Environment.getExternalStorageDirectory()+ File.separator+"erweima.png";
//
//                            File file = new File(Environment.getExternalStorageDirectory(), "erweima.png");
//                            Log.d("tag","图片==="+file.getAbsolutePath()+file.length());
//                            String b="data:image/png;base64,"+imageToBase64(file.getAbsolutePath());
//
//                            webView.evaluateJavascript("javascript:show('"+b+"')", new ValueCallback<String>() {
//                                @Override
//                                public void onReceiveValue(String value) {
//                                    //此处为 js 返回的结果
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        });

    }

    private class chromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){

            }
            super.onProgressChanged(view, newProgress);
        }
    }
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_WRAP);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
