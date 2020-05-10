package com.luck.cloud.widget.xrecycler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by liuyin on 2019/4/2 11:39
 * Description:RecyclerView条目间隔
 */
public class ItemLinearDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOffsetLeft = 0;    //分割线偏离左边界
    private int mOffsetRight = 0;   //分割线偏离右边界


    /**
     * 自定义分割线
     *
     * @param dividerHeight 分割线高度
     * @param offsetLeft    分割线距离左边距
     * @param offsetRight   分割线距离右边距
     * @param dividerColor  分割线颜色
     */
    public ItemLinearDivider(int dividerHeight, int offsetLeft, int offsetRight, int dividerColor) {
        mDividerHeight = dividerHeight;
        mOffsetLeft = offsetLeft;
        mOffsetRight = offsetRight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager)) {
            if (parent.getChildAdapterPosition(view) > 0) {//第一条顶部没有行间距
                int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
                int itemCount = parent.getAdapter().getItemCount();
                int position = parent.getChildAdapterPosition(view);
               // Log.e("tag", "childCount==" + childCount + "  itemCount" + itemCount + "  position==" + position + "  position2==" + position2);
                if (orientation == LinearLayoutManager.VERTICAL) {
                    //最后一条没有间距
                    if (position <= itemCount) {
                        outRect.set(0, mDividerHeight, 0, 0);
                    } else {
                        outRect.set(0, 0, 0, 0);
                    }
                } else {
                    outRect.set(0, 0, mDividerHeight, 0);
                }
            }

        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager) && !(parent.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    /**
     * 绘制纵向列表时的分隔线  这时分隔线是横着的
     * 每次 left相同，top根据child变化，right相同，bottom也变化
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        //当前页面条目数量
        int childSize = parent.getChildCount();
        //适配器列表条目数量
        int adapterCount = parent.getAdapter().getItemCount();
        //最后一条是加载更多,不需要显示分割线
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mPaint != null) {
                if (i < childSize - 2) {
                    canvas.drawRect(left + mOffsetLeft, top, right - mOffsetRight, bottom, mPaint);
                } else if (adapterCount >= childSize) {//最后一条数据如果适配器总条目多于显示条数
                    canvas.drawRect(left + mOffsetLeft, top, right - mOffsetRight, bottom, mPaint);
                }

            }
        }
    }

    /**
     * 绘制横向列表时的分隔线  这时分隔线是竖着的
     * l、r 变化； t、b 不变
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
