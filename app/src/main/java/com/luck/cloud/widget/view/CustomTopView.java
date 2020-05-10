package com.luck.cloud.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.cloud.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuyin on 2019/5/16 15:23
 * Description: 页面功能顶部信息
 */
public class CustomTopView extends RelativeLayout {
    //功能名称
    @Bind(R.id.tv_top_title)
    TextView mTvTitle;
    //菜单信息
    @Bind(R.id.tv_top_right_menu)
    TextView mTvRightMenu;
    //右侧图标
    @Bind(R.id.iv_top_right_icon)
    ImageView mIvRightIcon;

    public CustomTopView(Context context) {
        this(context, null);
    }

    public CustomTopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_top_title, this);
        ButterKnife.bind(this, view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTopTitle, defStyleAttr, 0);

        String title = array.getString(R.styleable.CustomTopTitle_title);
        String rightMenu = array.getString(R.styleable.CustomTopTitle_right_menu);
        Drawable icon = array.getDrawable(R.styleable.CustomTopTitle_right_icon);
        int textSize = array.getDimensionPixelSize(R.styleable.CustomTopTitle_title_size, 0);
        boolean titleBold = array.getBoolean(R.styleable.CustomTopTitle_title_bold, false);
        array.recycle();
        mTvTitle.setText(title);
        if (textSize != 0) mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTvRightMenu.setText(rightMenu);
//        if (titleBold) {
//            TextPaint paint = mTvTitle.getPaint();
//            paint.setFakeBoldText(true);
//        }


        if (icon != null) {
            mIvRightIcon.setVisibility(View.VISIBLE);
            mIvRightIcon.setImageDrawable(icon);
        } else {
            mIvRightIcon.setVisibility(View.GONE);
        }
    }

}
