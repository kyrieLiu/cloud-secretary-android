package com.luck.cloud.function.witness.dynamic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.cloud.FullyGridLayoutManager;
import com.luck.cloud.GlideEngine;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.function.witness.model.CommentModel;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:动态列表适配器
 */
public class CommentsAdapter<T extends CommentModel> extends BaseRecyclerViewAdapter<T> {


    public CommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
        @Bind(R.id.tv_comment)
        TextView tvComment;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @SuppressLint({"WrongConstant", "SetTextI18n"})
        @Override
        protected void bind(T bean, int p) {
            tvComment.setText(bean.getUserName()+":   "+bean.getMessageContent());
        }


    }
}
