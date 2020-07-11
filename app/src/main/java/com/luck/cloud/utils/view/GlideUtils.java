package com.luck.cloud.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luck.cloud.R;

/**
 * Created by liuyin on 2019/2/22 18:40
 * Glide 统一处理工具类
 */
public class GlideUtils {
    /**
     * 加载圆角图片
     *
     * @param context
     * @param path
     * @param imageView
     * @param radius
     */
    public static void loadRoundedCorners(Context context, ImageView imageView, String path, int radius, RoundedCornersTransformation.CornerType cornerType) {
        RequestOptions options = new RequestOptions()
                // .placeholder(R.mipmap.image_default_holder)
                .transforms(new CenterCrop(), new RoundedCornersTransformation(context, radius, 0, cornerType));


        Glide.with(context).load(path).apply(options)
                .into(imageView);
    }

    /**
     * 将占位图设置为圆角
     *
     * @param context
     * @param placeholderId
     * @return
     */
    private static RequestBuilder<Drawable> loadRoundTransform(Context context, @DrawableRes int placeholderId) {
        return Glide.with(context).load(placeholderId).apply(new RequestOptions().centerCrop().transform(new GlideCircleTransUtils(context)));
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param path
     */
    public static void loadCircleImage(Context context, ImageView imageView, String path) {
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.default_head_image)
                .transforms(new GlideCircleTransUtils(context));
        Glide.with(context).load(path)
                .apply(options)
                .thumbnail(loadTransform(context, R.mipmap.default_head_image))
                .into(imageView);
    }


    /**
     * 将占位图设置为圆角
     *
     * @param context
     * @param placeholderId
     * @return
     */
    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId) {
        return Glide.with(context).load(placeholderId).apply(new RequestOptions().centerCrop().transform(new GlideCircleTransUtils(context)));
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImage(Context context, ImageView imageView, String path) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.mipmap.image_default_holder)
                .error(R.mipmap.image_default_holder);
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImage(Context context, ImageView imageView, String path,int placeId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeId)
                .error(placeId);
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    /**
     * 加载长图
     *
     * @param context
     * @param path      路径
     * @param imageView
     */
    public static void loadLongImage(Context context, final ImageView imageView, String path) {
        //noinspection deprecation
        Glide.with(context)
                .asBitmap() //指定格式为Bitmap
                .load(path)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed( GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        //加载失败
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        //加载成功，resource为加载到的bitmap
                        imageView.setAdjustViewBounds(true);
                        imageView.setImageBitmap(resource);
                        return false;
                    }
                })
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);//加载原图大小

    }

    /**
     * 加载gif图
     *
     * @param context
     * @param imageView
     * @param res
     */
    public static void loadGif(Context context, ImageView imageView, int res) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.image_default_holder)
                .error(R.mipmap.image_default_holder);
        Glide.with(context).asGif().load(res).apply(options).into(imageView);
    }


}
