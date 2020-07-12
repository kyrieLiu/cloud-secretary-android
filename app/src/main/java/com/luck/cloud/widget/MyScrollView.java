package com.luck.cloud.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 描述：自定义ScrollView解决自动滑动到中间的问题和判断是否滑动到底部
 *
 * @author wangjian
 */
public class MyScrollView extends ScrollView {
    private int page = 1;
    private OnScroll onScroll;
    private OnScrollLoad onScrollLoad;
    private boolean scroll = false;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (onScroll != null) {
            onScroll.onScroll(scrollX, scrollY, clampedX, clampedY);

        }
        if (scrollY > 0 && clampedY) {
            if (!scroll) {
                scroll = true;
                if (onScrollLoad != null) {
                    page++;
                    onScrollLoad.onLoad(page);
                }
            }
        } else {
            scroll = false;
        }
    }

    /**
     * 解决套用后 不置顶的问题
     */
    protected int computeScrollDeltaToGetChildRectOnScreen(
            android.graphics.Rect rect) {
        return 0;
    }

    ;

    public void setOnScroll(OnScroll onScroll) {
        this.onScroll = onScroll;
    }

    public void setOnScrollLoad(OnScrollLoad onScrollLoad) {
        this.onScrollLoad = onScrollLoad;
    }

    public interface OnScroll {
        public void onScroll(int scrollX, int scrollY, boolean clampedX,
                             boolean clampedY);
    }

    public interface OnScrollLoad {
        public void onLoad(int page);
    }


    /**
     * 滚动状态 IDLE 滚动停止 TOUCH_SCROLL 手指拖动滚动 FLING滚动
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }

    /**
     * 记录当前滚动的距离
     */
    private int currentY = -9999999;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            if (getScrollY() == currentY) {
                // 滚动停止 取消监听线程
                scrollType = ScrollType.IDLE;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollStateChanged(MyScrollView.this, scrollType);
                }
                if (mHandler != null) {
                    mHandler.removeCallbacks(this);
                }
                return;
            } else {
                // 手指离开屏幕 view还在滚动的时候
                scrollType = ScrollType.FLING;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollStateChanged(MyScrollView.this, scrollType);
                }
            }
            currentY = getScrollY();
            mHandler.postDelayed(this, scrollDealy);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollStateChanged(MyScrollView.this, scrollType);
                }
                // 手指在上面移动的时候 取消滚动监听线程
                if (mHandler != null) {
                    mHandler.removeCallbacks(scrollRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 手指移动的时候
                if (mHandler != null) {
                    mHandler.post(scrollRunnable);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 必须先调用这个方法设置Handler,不然会出错/或者设置监听的时候传进来
     *
     * @param handler
     * @return void
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    private Handler mHandler;
    private OnScrollStateChangeListener scrollViewListener;

    public interface OnScrollStateChangeListener {

        void onScrollStateChanged(MyScrollView myScrollView, ScrollType scrollType);

    }

    /**
     * 设置滚动监听
     *
     * @param listener
     * @return void
     */
    public void setOnScrollStateChangedListener(
            OnScrollStateChangeListener listener, Handler handler) {
        this.scrollViewListener = listener;
        this.mHandler = handler;
    }



    private OnScollChangedListener onScollChangedListener = null;
    public void setOnScollChangedListener(OnScollChangedListener onScollChangedListener) {
        this.onScollChangedListener = onScollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScollChangedListener != null) {
            onScollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScollChangedListener {

        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    float xDistance, yDistance, xLast, yLast;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance) {//如果是左右滑动不拦截事件传递
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
