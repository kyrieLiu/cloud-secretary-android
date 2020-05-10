package com.luck.cloud.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.function.login.LoginActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;

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
        //webUrl = "http://debugtbs.qq.com";
        //webUrl = "https://vodkgeyttp8.vod.126.net/cloudmusic/IiUyIDAwMGA0IDQhNCAhMQ==/mv/5678292/3e508549183aadc029aec6d37cc32aac.mp4?wsSecret=6d33dad586e1a369c95826d622658fa0&wsTime=1560319430";
        //webUrl = "http://www.ytmp3.cn/down/73693.mp3";
        //webUrl = "http://ac.test.jingcaiwang.cn:18080/assetH5/index.html#/HouseRental?token=customerce031cec9e4f4793aced6dd30b1fe41c";

        Log.e("tag", "URL==" + webUrl);

    }

    @Override
    protected void loadData() {
        initWebView();

//        String body="<p>史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇女，" +
//                "那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
//                "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高" +
//                "房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
//                "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻" +
//                "烦你给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对" +
//                "高房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
//                "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
//                "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高房价是" +
//                "的分开管理<img src=\"http://wgzx.test.jingcaiwang.cn/group1/M00/00/68/rBMBOF0J-8WABHG6AAGBabvhhYo556.png\" title=\" 000.png\" alt=\" 000.png\" style=\"white-space: normal;\"/></p>";
        String html = ViewUtil.getViewUtil().getHtmlData(Temporary.webContent);
        webView.loadData(html, "text/html;charset=utf-8","utf-8");
        //webView.loadUrl(webUrl);
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
