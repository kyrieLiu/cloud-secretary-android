package com.luck.cloud.widget.xrecycler;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by liuyin on 2019/4/1 16:36
 * Description:RecyclerView网格布局设置item间距
 */
public class ItemGridDecoration extends RecyclerView.ItemDecoration{

    private int landscapeSpacing;//横向间隔
    private int verticalSpacing;//垂直间隔
    private int spanCount;//列数


    public ItemGridDecoration(int landscapeSpacing, int verticalSpacing, int spanCount) {
        this.landscapeSpacing = landscapeSpacing;
        this.verticalSpacing = verticalSpacing;
        this.spanCount=spanCount;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 条目下标
        int column = position % spanCount; // 判断列数
        outRect.left = column * landscapeSpacing / spanCount;
        outRect.right = landscapeSpacing - (column + 1) * landscapeSpacing / spanCount;
        if (position >= spanCount) {
            outRect.top = verticalSpacing;
        }
    }


}
