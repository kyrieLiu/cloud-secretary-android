package com.luck.cloud.function.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.home.HomeMenuBean;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;

import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/2/21 14:00
 * 首页功能菜单
 */
public class MineMenuAdapter<T extends HomeMenuBean> extends BaseRecyclerViewAdapter<T> {



    public MineMenuAdapter(List<T> list, Context context) {
        super(list, context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_menu, parent, false);
        return new MyViewHolder(view);
    }


    class MyViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.iv_mine_menu_icon)
        ImageView ivMineMenuIcon;
        @Bind(R.id.tv_mine_menu_name)
        TextView tvMineMenuName;
        @Bind(R.id.iv_mine_menu_right)
        ImageView ivRightIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int position) {

            Sharp sharp= Sharp.loadResource(context.getResources(), R.raw.right_icon);
            sharp.setOnElementListener(new OnSvgElementListener() {
                @Override
                public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF rectF) {

                }

                @Override
                public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF rectF) {

                }

                @Override
                public <T> T onSvgElement(@Nullable String s, @NonNull T t, @Nullable RectF rectF, @NonNull Canvas canvas, @Nullable RectF rectF1, @Nullable Paint paint) {
                    paint.setColor(Color.parseColor("#DDDDDD"));
                    return t;
                }

                @Override
                public <T> void onSvgElementDrawn(@Nullable String s, @NonNull T t, @NonNull Canvas canvas, @Nullable Paint paint) {

                }
            });
            sharp.into(ivRightIcon);

            tvMineMenuName.setText(bean.getMenuName());
            ivMineMenuIcon.setImageResource(bean.getIconPath());
        }
    }

}


