package com.luck.cloud.common.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.luck.cloud.function.mine.work.DateUtil;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.function.login.LoginActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

import butterknife.Bind;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by liuyin on 2019/4/15 14:28
 * Describe: 加载Web页面
 */
public class WebActivity extends BaseActivity {

    @Bind(R.id.pb_webview_load)
    ProgressBar progressBar;
    @Bind(R.id.wv_web)
    WebView webView;

    private String imgurl = "";
    private String webUrl;

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
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("详情");

        webUrl = getIntent().getStringExtra("url");

    }

    @Override
    protected void loadData() {
        initWebView();
        if (webUrl!=null){
            webView.loadUrl(webUrl);
        }else{
            String html = ViewUtil.getViewUtil().getHtmlData(Temporary.webContent);
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    public void initWebView() {

        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setGeolocationEnabled(true);
        // webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
//        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);


        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void h5_finish() {
                WebActivity.this.finish();
            }

            @JavascriptInterface
            public void h5_complete() {
                setResult(10);
                WebActivity.this.finish();
            }

            @JavascriptInterface
            public void token_past_due() {//token过期
                ToastUtil.toastShortCenter("token已过期,请重新登录");
                LoginActivity.start(WebActivity.this);
            }

            @JavascriptInterface
            public void processHTML(String html) {
                Log.e("tag", "html===" + html);
            }
        }, "android");

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

    }

    // Web视图
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //加载完成后可以调用这个查看HTML源码
            //webView.loadUrl("javascript:android.processHTML(document.documentElement.outerHTML);");

        }
    }

    protected class MyWebChromeClient extends WebChromeClient {



        @Override
        public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {

            Log.d("s===",s);
            Log.d("s1===",s1);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            Log.d("tag",fileChooserParams.getMode()+"");
            Log.d("tag",fileChooserParams.isCaptureEnabled()+"");
            return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(VISIBLE);
            }
            progressBar.setProgress(newProgress);
        }


    }


    /***
     * 功能：长按图片保存到手机
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "保存到手机") {
                    downLoadFile(imgurl);
                } else {
                    return false;
                }
                return true;
            }
        };
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            if (result != null) {
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgurl = result.getExtra();
                    menu.setHeaderTitle("提示");
                    menu.add(0, v.getId(), 0, "保存到手机").setOnMenuItemClickListener(handler);
                }
            }
        }
    }


    /**
     * 下载文件
     */
    public void downLoadFile(String path) {
        File file = new File(Environment.getExternalStorageDirectory(), AppConstants.SD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        File recordFile = new File(file, "cache.jpg");
        OKHttpManager.getInstance().getDownloadDelegate().downloadAsynWithProgress(path, recordFile, new OKHttpManager.ProgressCallback() {

            @Override
            protected void onProgressChanged(float percent, long length) {
            }

            @Override
            public void onDownloadFinish(String fileAbsolutePath) {
                ToastUtil.toastLongCenter("下载完成");
            }

            @Override
            public void onDownloadError(String error) {
            }
        }, this);
        //  download(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            //webView.clearHistory();
            webView.clearCache(true);
            //webView.pauseTimers();
            webView.destroy();
            webView = null;
        }

    }
}
