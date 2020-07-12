package com.luck.cloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 描述：重写ListView 解决ScrollView冲突
 * 
 * @author wanjian
 * 
 */
public class MyListView extends ListView
{
    public MyListView(Context context)
    {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}