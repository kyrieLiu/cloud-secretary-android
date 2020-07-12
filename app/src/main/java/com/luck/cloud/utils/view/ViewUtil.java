package com.luck.cloud.utils.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.luck.cloud.app.AppApplication;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.utils.JsonUtils;
import com.luck.cloud.utils.LogUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuyin on 2018/8/10 11:22
 * 用于操作View
 */
public class ViewUtil {
    private volatile static ViewUtil viewUtil;
    private ArrayList<String> pathimgs;

    private StyleSpan styleSpan;

    public static ViewUtil getViewUtil() {
        if (viewUtil == null) {
            synchronized (ViewUtil.class) {
                viewUtil = new ViewUtil();
            }
        }
        return viewUtil;
    }


    private List<String> pictureList = null;

//    /**
//     * 公用加载图片以及查看大图
//     *
//     * @param context
//     * @param picturePath
//     * @param gvPictures
//     */
//    public void showGridImage(final Context context, String picturePath, NoScrollGridView gvPictures, LinearLayout llPicParent) {
//        if (TextUtils.isEmpty(picturePath)) {
//            llPicParent.setVisibility(View.GONE);
//            return;
//        } else {
//            llPicParent.setVisibility(View.VISIBLE);
//        }
//        //展示小图大图
//        try {
//            pictureList = JsonUtils.jsonToList(picturePath, String.class);
//        } catch (Exception e) {
//            LogUtil.e("异常图片地址" + picturePath);
//
//            String[] pathArr = picturePath.split(",");
//            pictureList = Arrays.asList(pathArr);
//        }
//        if (pictureList == null || pictureList.size() == 0) { // 没有图片资源就隐藏GridView
//            llPicParent.setVisibility(View.GONE);
//        } else {
//            llPicParent.setVisibility(View.VISIBLE);
//            final ArrayList<String> appendList = new ArrayList<>();
//            for (int i = 0; i < pictureList.size(); i++) {
//                appendList.add(URLConstant.BASE_URL + pictureList.get(i));
//            }
//            int columnNum = gvPictures.getNumColumns();
//            NoScrollGridAdapter adapter = new NoScrollGridAdapter(context, appendList, columnNum);
//            gvPictures.setAdapter(adapter);
//            // 点击回帖九宫格，查看大图
//            gvPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    imageBrower(context, position, appendList);
//                }
//            });
//        }
//    }
//
//
//    /**
//     * 打开图片查看器
//     *
//     * @param position
//     * @param urls2
//     */
//    protected void imageBrower(Context context, int position, List<String> urls2) {
//        Intent intent = new Intent(context, ImagePagerActivity.class);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (Serializable) urls2);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
//        context.startActivity(intent);
//    }
//

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    public String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, maximum-scale=1, minimum-scale=1, user-scale=1\"> " +
                "<style type=\"text/css\">" +
                "p{word-wrap: break-word;}" +
                "img{width: 100%;height: 100%;max-height:450px;}" +
                "</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    /**
     * 将数据填充到对应控件上
     *
     * @param textView
     * @param title
     * @param message
     */
    public void setTextMessage(TextView textView, String title, String message) {
        if (TextUtils.isEmpty(message)) {
            textView.setText(title + ": ");
        } else {
            textView.setText(title + ": " + message);
        }
    }

    //设置不同型号字体
    public void setDifferentSizeText(String title, String content, TextView textView) {
        // if (styleSpan == null) styleSpan = new StyleSpan(Typeface.BOLD);
        if (content == null) content = "";
        Spannable string = new SpannableString(title + "  " + content + "");
        string.setSpan(new AbsoluteSizeSpan(15, true), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(string);
    }

    //设置不同型号字体和颜色
    public void setDifferentColorText(String title,String content,TextView textView){
        Spannable string = new SpannableString(title + "  " + content + "");
        string.setSpan(new AbsoluteSizeSpan(15, true), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),0,title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(string);
    }

    //设置不同型号字体
    public void setDifferentSizeText(String title, String content, TextView textView, int preSize) {
        if (styleSpan == null) styleSpan = new StyleSpan(Typeface.BOLD);

        if (content == null) content = "";
        Spannable string = new SpannableString(title + "  " + content + "");
        string.setSpan(new AbsoluteSizeSpan(preSize, true), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(string);
    }


    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static int[] getScreenSize() {
        WindowManager wm = (WindowManager) AppApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    public static int dp2px(int dp) {
        DisplayMetrics dm = AppApplication.getInstance().getResources().getDisplayMetrics();
        int px = (int) ((double) ((float) dp * dm.density) + 0.5D);
        return px;
    }

    /**
     * 获取对应xml中的dp
     *
     * @param context
     * @param dp
     * @return
     */
    public static int getDp(Context context, int dp) {
        int size = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
        return size;
    }

    /**
     * 转化金钱格式
     *
     * @param money
     * @return
     */
    public static String turnMoneyFormat(double money) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(money);
    }
}
