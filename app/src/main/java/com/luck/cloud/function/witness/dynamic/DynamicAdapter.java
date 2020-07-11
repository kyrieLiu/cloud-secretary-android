package com.luck.cloud.function.witness.dynamic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.cloud.FullyGridLayoutManager;
import com.luck.cloud.GlideEngine;
import com.luck.cloud.PictureMainActivity;
import com.luck.cloud.R;
import com.luck.cloud.adapter.GridImageAdapter;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.function.main.MainActivity;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.ScreenUtils;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:动态列表适配器
 */
public class DynamicAdapter<T extends SuperviseHandleBean.ItemsBean> extends BaseRecyclerViewAdapter<T> {


    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
        @Bind(R.id.tv_dynamic_attention)
        TextView attention;
        @Bind(R.id.rv_dynamic_pictures)
        RecyclerView mRecyclerView;
        @Bind(R.id.iv_dynamic_video)
        ImageView ivVideo;
        @Bind(R.id.rl_video_parent)
        RelativeLayout rlVideoParent;
        @Bind(R.id.ll_dynamic_like)
        LinearLayout mLlLike;
        @Bind(R.id.iv_dynamic_like)
        ImageView ivLike;
        @Bind(R.id.tv_dynamic_like)
        TextView tvLike;

        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int p) {


            Sharp sharp= Sharp.loadResource(context.getResources(), R.raw.like);
            sharp.setOnElementListener(new OnSvgElementListener() {
                @Override
                public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF rectF) { }

                @Override
                public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF rectF) { }

                @Override
                public <T> T onSvgElement(@Nullable String s, @NonNull T t, @Nullable RectF rectF, @NonNull Canvas canvas, @Nullable RectF rectF1, @Nullable Paint paint) {
//                    Random random = new Random();
//                    paint.setColor(Color.parseColor("#ffffff"));
                    return t;
                }

                @Override
                public <T> void onSvgElementDrawn(@Nullable String s, @NonNull T t, @NonNull Canvas canvas, @Nullable Paint paint) { }
            });
            sharp.into(ivLike);


            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(1, Color.parseColor("#FFCB02"));
            attention.setBackground(drawable);


            String videoUrl="http://124.70.179.180/group1/M00/00/00/wKgAHl8JWpKAR-9DATvtgCPh_mk436.mp4";

//            RequestOptions options = new RequestOptions()
//                    .placeholder(R.mipmap.image_default_holder)
//                    .transforms(new CenterCrop(), new RoundedCornersTransformation(context, radius, 0, cornerType));

                Glide.with(context).load(videoUrl)
                        .into(ivVideo);



            rlVideoParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            FullyGridLayoutManager manager = new FullyGridLayoutManager(context,
                    3, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(manager);

            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                    ScreenUtils.dip2px(context, 8), false));
            DynamicPictureAdapter<LocalMedia> mAdapter = new DynamicPictureAdapter(context);
            List<LocalMedia> list = new ArrayList<>();
            for (int i=0;i<5;i++){
                LocalMedia media=new LocalMedia();
                String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588528793769&di=ebef5b108b41960c01a2ad44060b7935&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200403%2F5e2749e286b34b7da9e9197d19950728.jpeg";
                media.setPath(url);
                list.add(media);
            }


            mAdapter.setList(list);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
                @Override
                public void onItemClick(View view, int position) {


                    PictureSelector.create((Activity) context)
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, list);

                }
            });

        }
    }
}
