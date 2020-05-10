package com.luck.cloud.utils;

import android.content.Context;
import android.widget.ImageView;

import com.luck.cloud.utils.view.GlideUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by liuyin on 2019/5/15 15:35
 * Describe: banner加载图片holder
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        if (path instanceof String){
            GlideUtils.loadImage(context, imageView, (String) path);
        }else if (path instanceof Integer){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource((Integer) path);
        }



    }
}
