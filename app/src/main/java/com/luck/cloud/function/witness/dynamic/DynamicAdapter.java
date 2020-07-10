package com.luck.cloud.function.witness.dynamic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        public Bitmap getNetVideoBitmap(String videoUrl) {
            Bitmap bitmap = null;

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                //根据url获取缩略图
                retriever.setDataSource(videoUrl, new HashMap());
                //获得第一帧图片
                bitmap = retriever.getFrameAtTime();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                retriever.release();
            }
            return bitmap;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int p) {


            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(1, Color.parseColor("#FFCB02"));
            attention.setBackground(drawable);

            String videoUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588528793769&di=ebef5b108b41960c01a2ad44060b7935&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200403%2F5e2749e286b34b7da9e9197d19950728.jpeg";

            GlideUtils.loadRoundedCorners(context,ivVideo,videoUrl,10, RoundedCornersTransformation.CornerType.ALL);

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
