package com.luck.cloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by liuyin on 2019/2/27 16:47
 * Description:解决scrollView嵌套RecyclerView,导致RecyclerView条目显示不全问题
 */
public class MeasureRecyclerView extends RecyclerView {
    public MeasureRecyclerView(Context context) {
        super(context);
    }

    public MeasureRecyclerView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        //限制RecyclerView自身的滑动，页面滑动仅依靠ScrollView实现
        setHasFixedSize(true);
        setNestedScrollingEnabled(false);
    }

    public MeasureRecyclerView(Context context,  AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
