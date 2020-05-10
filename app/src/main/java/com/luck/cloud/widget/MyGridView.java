package com.luck.cloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 描述：重写GridView 解决ScrollView冲突
 *
 */

public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

//    private int downPoint;
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downPoint = (int) ev.getY();
//                LogUtil.d("按下去坐标y==" + downPoint);
//                getParent().requestDisallowInterceptTouchEvent(true);//父布局是ScrollView,需取消父类事件拦截
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int y = (int) ev.getY();
//                int distance = y - downPoint;
//                if (distance > 40 || distance < -40) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }


}