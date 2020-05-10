package com.luck.cloud.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.X5WebView;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/6/13 9:39
 * Describe: 用浏览器播放语音和视频
 */
public class FileBrowserActivity extends BaseActivity {
    @Bind(R.id.full_web_webview)
    X5WebView webView;
    String id ;

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, FileBrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_file;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)){
            // 仅物业服务标准
            requestDetailData();
        }else {
            String url = getIntent().getStringExtra("url");
            loadUrl(url);
        }


//        url="http://pocketbook.document.jingcaiwang.cn/group1/M00/00/6B/rBMBOF0LLdSAGFtHAFXulOG0dBM72.docx";
//
//        String content="http://view.officeapps.live.com/op/view.aspx?src="+url;
//        Log.e("tag","content=="+content);

//        String body = "<video style=\"width:100%; height: auto;\" controls>" +
//                "<source src=https://vodkgeyttp8.vod.126.net/cloudmusic/ISMwYjAwYiRgICAgICQxZA==/mv/524116/40666547037b3cc67840d5f5b625ed73.mp4?wsSecret=4055a8c9d07e651c62e1c7c1dc9677de&wsTime=1560670650>" +
//                "</video>";
//
//        String html = ViewUtil.getViewUtil().getHtmlData(body);
//
         //url = "https://vodkgeyttp8.vod.126.net/cloudmusic/ISMwYjAwYiRgICAgICQxZA==/mv/524116/40666547037b3cc67840d5f5b625ed73.mp4?wsSecret=4055a8c9d07e651c62e1c7c1dc9677de&wsTime=1560670650";
//        webView.loadData(html, "text/html;charset=utf-8","utf-8");

    }


    /**
     * 加载url
     */
    private void loadUrl(String url){

        if(TextUtils.isEmpty(url)){
            return;
        }

        webView.loadUrl(url);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public void onX5ButtonClicked() {
                FileBrowserActivity.this.enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                FileBrowserActivity.this.disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                FileBrowserActivity.this.enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                FileBrowserActivity.this.enablePageVideoFunc();
            }
        }, "Android");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // /////////////////////////////////////////
    // 向webview发出信息
    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    /**
     * 获取详情数据
     */
    private void requestDetailData(){
//        params.clear();
//        params.put("id",id);
//        showRDialog();
//        OKHttpManager.postJsonRequest(URLConstant.GET_DOC_DETAIL, params, new OKHttpManager.ResultCallback<BaseBean<PropertyServiceStandardBean.ItemsBean>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//            }
//
//            @Override
//            public void onResponse(BaseBean<PropertyServiceStandardBean.ItemsBean> response) {
//                hideRDialog();
//                PropertyServiceStandardBean.ItemsBean itemsBean = response.getData();
//                if (itemsBean != null){
//                    loadUrl(response.getData().getDocUrl());
//                }
//            }
//        },this);
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (webView != null) {
//            webView.stopLoading();
//            webView.clearHistory();
//            webView.clearCache(true);
//            webView.pauseTimers();
//            webView.destroy();
//            webView = null;
//        }
//    }
}
