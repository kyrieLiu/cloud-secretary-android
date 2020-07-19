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
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.dynamic.add.AddDynamicActivity;
import com.luck.cloud.function.witness.model.CommentModel;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.function.witness.model.DynamicModel.RecordsBean;
import com.luck.cloud.utils.SpUtil;
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
public class DynamicAdapter<T extends DynamicModel.RecordsBean> extends BaseRecyclerViewAdapter<T> {

    private ItemClickListener clickListener;

    public DynamicAdapter(Context context) {
        super(context);
    }

    public void setClickListener(ItemClickListener listener) {
        this.clickListener = listener;
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
        @Bind(R.id.ll_dynamic_comment)
        LinearLayout llComment;
        @Bind(R.id.iv_dynamic_comment)
        ImageView ivComment;
        @Bind(R.id.ll_dynamic_transmit)
        LinearLayout llTransimit;
        @Bind(R.id.iv_dynamic_transmit)
        ImageView ivTransmit;
        @Bind({R.id.rv_dynamic_comment_list})
        RecyclerView rlCommentList;
        @Bind(R.id.tv_dynamic_like_statistic)
        TextView tvLikeStatistic;
        @Bind(R.id.iv_dynamic_like_statistic)
        ImageView ivLikeStatistic;
        @Bind(R.id.ll_like_statistic)
        LinearLayout llLikeStatistic;
        @Bind(R.id.ll_dynamic_container)
        LinearLayout container;
        @Bind(R.id.ll_dynamic_collect)
        LinearLayout llCollect;
        @Bind(R.id.iv_dynamic_collect)
        ImageView ivCollect;
        @Bind(R.id.tv_dynamic_collect)
        TextView tvCollect;
        @Bind(R.id.tv_dynamic_text)
        TextView tvContent;
        @Bind(R.id.iv_dynamic_head)
        ImageView ivHead;
        @Bind(R.id.tv_dynamic_people)
        TextView username;
        @Bind(R.id.tv_dynamic_time)
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int p) {

            Sharp sharp = Sharp.loadResource(context.getResources(), R.raw.like);
            sharp.setOnElementListener(new OnSvgElementListener() {
                @Override
                public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF rectF) {
                }

                @Override
                public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF rectF) {
                }

                @Override
                public <T> T onSvgElement(@Nullable String s, @NonNull T t, @Nullable RectF rectF, @NonNull Canvas canvas, @Nullable RectF rectF1, @Nullable Paint paint) {
                    if (bean.getIsLike() ==1) {
                        //已点赞
                        assert paint != null;
                        paint.setColor(Color.parseColor("#ff0000"));
                        tvLike.setTextColor(Color.parseColor("#ff0000"));
                        tvLike.setText("取消点赞");
                    } else {
                        paint.setColor(context.getResources().getColor(R.color.main_text_color));
                        tvLike.setTextColor(context.getResources().getColor(R.color.main_text_color));
                        tvLike.setText("点赞");
                    }

                    return t;
                }

                @Override
                public <T> void onSvgElementDrawn(@Nullable String s, @NonNull T t, @NonNull Canvas canvas, @Nullable Paint paint) {
                }
            });
            sharp.into(ivLike);

            Sharp collectSharp = Sharp.loadResource(context.getResources(), R.raw.collect);
            collectSharp.setOnElementListener(new OnSvgElementListener() {
                @Override
                public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF rectF) {
                }

                @Override
                public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF rectF) {
                }

                @Override
                public <T> T onSvgElement(@Nullable String s, @NonNull T t, @Nullable RectF rectF, @NonNull Canvas canvas, @Nullable RectF rectF1, @Nullable Paint paint) {
                    if (bean.getIsCollect() == 1) {
                        assert paint != null;
                        paint.setColor(Color.parseColor("#ff0000"));
                        tvCollect.setTextColor(Color.parseColor("#ff0000"));
                        tvCollect.setText("取消收藏");
                    } else {
                        tvCollect.setTextColor(context.getResources().getColor(R.color.main_text_color));
                        tvCollect.setText("收藏");
                    }

                    return t;
                }

                @Override
                public <T> void onSvgElementDrawn(@Nullable String s, @NonNull T t, @NonNull Canvas canvas, @Nullable Paint paint) {
                }
            });
            collectSharp.into(ivCollect);

            Sharp shComment = Sharp.loadResource(context.getResources(), R.raw.comment);
            shComment.into(ivComment);
            Sharp shTransmit = Sharp.loadResource(context.getResources(), R.raw.transmit);
            shTransmit.into(ivTransmit);
            Sharp shStatisticLike = Sharp.loadResource(context.getResources(), R.raw.like);
            shStatisticLike.into(ivLikeStatistic);
//            if (bean.getLikeUsers() != null && bean.getLikeUsers().size() > 0) {
//                tvLikeStatistic.setText(String.join(",", bean.getLikeUsers()));
//                llLikeStatistic.setVisibility(View.VISIBLE);
//
//            } else {
//                llLikeStatistic.setVisibility(View.GONE);
//            }

            tvLikeStatistic.setText(String.valueOf(bean.getLikeCount()));


            List<CommentModel> commentList = bean.getMessages();
            CommentsAdapter<CommentModel> commentsAdapter = new CommentsAdapter(context);
            commentsAdapter.setList(commentList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            rlCommentList.setLayoutManager(layoutManager);
            rlCommentList.setAdapter(commentsAdapter);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);
            drawable.setStroke(1, Color.parseColor("#FFCB02"));
            attention.setBackground(drawable);

            mLlLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.likeCallback(bean,p);
                }
            });
            llComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.commentCallback(bean, p);
                }
            });

            llTransimit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.transmitCallback(bean,p);
                }
            });


            llCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.collectCallback(bean,p);
                }
            });

            if (bean.getFileType()==1) {
                rlVideoParent.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                //图片
                FullyGridLayoutManager manager = new FullyGridLayoutManager(context,
                        3, GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(manager);
                DynamicPictureAdapter<LocalMedia> mAdapter = new DynamicPictureAdapter(context);
                List<LocalMedia> list = new ArrayList<>();
                String[] images=bean.getDyFile().split(";");
                for (String path:images){
                    LocalMedia media = new LocalMedia();
                    media.setPath(path);
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

            }else{
                rlVideoParent.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);

                String videoPicture=bean.getSurfacePlot();
                GlideUtils.loadRoundedCorners(context, ivVideo, videoPicture, 4, RoundedCornersTransformation.CornerType.ALL);

                ivVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.playVideoCallback(bean,p);
                    }
                });
            }
            tvContent.setText(bean.getContent());

            GlideUtils.loadCircleImage(context,ivHead,bean.getUserPhoto());

            username.setText(bean.getUserName());
            time.setText(DateUtil.getUnderlineDay(bean.getCreateTime()));

            if (SpUtil.getUerId()==bean.getUserId()){
                attention.setVisibility(View.GONE);
            }else{
                attention.setVisibility(View.VISIBLE);
            }
            if (bean.getIsAttention()==1){
                attention.setText("取消关注");
            }else{
                attention.setText("关注");
            }

            attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.attentionCallback(bean,p);
                }
            });

//            if (bean.getIsLike()==1){
//                tvLike.setText("取消点赞");
//            }else{
//                tvLike.setText("点赞");
//            }


        }
    }

    public interface ItemClickListener {
        void commentCallback(DynamicModel.RecordsBean model, int position);

        void transmitCallback(DynamicModel.RecordsBean model, int position);
        void likeCallback(DynamicModel.RecordsBean model,int position);
        void deleteCallback(DynamicModel.RecordsBean model, int position);
        void collectCallback(DynamicModel.RecordsBean model,int position);
        void playVideoCallback(DynamicModel.RecordsBean model,int position);
        void attentionCallback(DynamicModel.RecordsBean model,int position);
    }
}
