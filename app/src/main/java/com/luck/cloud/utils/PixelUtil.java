package com.luck.cloud.utils;

import android.content.Context;

import com.luck.cloud.app.AppApplication;


/**
 * Created by liuyin on 2019/2/15 9:25
 * 像素处理工具
 */
public class PixelUtil {

    public static int dip2px(Context context, float dpValue) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public int dip2px(float dpValue) {

        float scale = AppApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);

    }

}
