package com.luck.cloud.function.mine.work;

/**
 * Created by jiang_yan on 2017/9/30.
 */


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;


public abstract class CommentPopUtils
{
    private View v;
    protected PopupWindow popupWindow;
    protected OnClickListener onClickListener;
    protected OnItemClickListener itemClickListener;
    protected OnDismissListener onDismissListener;
    protected Context context;
    public View view;

    public CommentPopUtils(View v, Context context, int layout)
    {
        super();
        this.v = v;
        view = LayoutInflater.from(context).inflate(layout, null);
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setOnDismissListener(new OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                popupWindow.dismiss();
                if (onDismissListener != null)
                {
                    onDismissListener.onDismiss();
                }
            }
        });
        initLayout(view, context);


    }
    public CommentPopUtils(Context context){
        this.context=context;
    }



    public void setContentLayout(int layout,int...layoutParams){
        view = LayoutInflater.from(context).inflate(layout, null);

        if (layoutParams!=null&&layoutParams.length>1){
            popupWindow = new PopupWindow(view, layoutParams[0], layoutParams[1]);
        }else{
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setOnDismissListener(new OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                popupWindow.dismiss();
                if (onDismissListener != null)
                {
                    onDismissListener.onDismiss();
                }
            }
        });
        initLayout(view, context);
    }

    public CommentPopUtils(Context context, int layout)
    {
        super();
        this.context=context;
        view = LayoutInflater.from(context).inflate(layout, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                //popupWindow.dismiss();
                if (onDismissListener != null)
                {
                    onDismissListener.onDismiss();
                }
            }
        });
        initLayout(view, context);


    }

    public void setOnDismissListener(OnDismissListener onDismissListener)
    {
        this.onDismissListener = onDismissListener;
    }

    public abstract void initLayout(View v, Context context);

    /**
     * 指定控件下方弹出
     *

     */
    @SuppressWarnings("deprecation")
    public void showAsDropDown(View v)
    {
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAsDropDown(v);

    }

    @SuppressWarnings("deprecation")
    public void showPopUp(View v)
    {

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.TOP, 15, location[1] - popupWindow.getHeight());
        popupWindow.update();
    }

    /**
     * 下拉式 弹出 pop菜单 parent 右下角
     *
     */
    @SuppressWarnings("deprecation")
    public void showAsDropDownInstance(View view)
    {
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置弹出位置
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // 刷新状态
        popupWindow.update();

    }

    /**
     * 下拉式 弹出 pop菜单 parent 右下角
     *
     */
    @SuppressWarnings("deprecation")
    public void showAtLocation()
    {
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        // 刷新状态
        popupWindow.update();

    }
    public void showBottom(){
        popupWindow.showAtLocation(popupWindow.getContentView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 隐藏菜单
     */
    public void dismiss()
    {
        popupWindow.dismiss();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }



    /**
     * 显示在指定位置下方
     * @param assignView
     * @param x
     * @param y
     */
    public void showAtDownLocation(View assignView, int x, int y){
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //兼容api24
        if (assignView != null && Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            assignView.getGlobalVisibleRect(rect);
            int heightPixel=assignView.getResources().getDisplayMetrics().heightPixels;
            int bottom=rect.bottom;
            int h = heightPixel - bottom;
            popupWindow.setHeight(h);
        }
        popupWindow.showAsDropDown(assignView,x,y);
    }

    /**
     * 显示在指定位置下方
     * @param assignView
     */
    public void showAtRight(View assignView, int... pixel){
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //兼容api24
//        if (assignView != null && Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            assignView.getGlobalVisibleRect(rect);
//            int h = assignView.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            popupWindow.setHeight(h);
//        }
        if (pixel!=null){
            popupWindow.setWidth(pixel[0]);
        }


        popupWindow.showAtLocation(assignView, Gravity.RIGHT,0,0);
    }

}
