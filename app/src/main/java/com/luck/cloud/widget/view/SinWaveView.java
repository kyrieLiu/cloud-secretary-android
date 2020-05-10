package com.luck.cloud.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liuyin on 2019/4/9 14:19
 * Describe: 个人中心顶部背景动态图
 */
public class SinWaveView extends View {
    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mWavePaint, mWhitePaint;
    //初始值
    private float mOffset1 = 10f;
    private float mOffset2 = 220f;
    private float mOffset3 = 90f;
    private float mOffset4 = 0.0f;
    //波动速度
    private float mSpeed1 = 0.005f;
    private float mSpeed2 = 0.01f;
    private float mSpeed3 = 0.015f;
    private float mSpeed4 = 0.02f;

    private int viewHeight;//View高度
    private int viewWidth;//View宽度

    private int waveHeight = 85;//浪高


    public SinWaveView(Context context) {
        super(context);
        init();
    }

    public SinWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SinWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {


        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(0x880074D0);
        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setColor(Color.parseColor("#f7f7f7"));
        mWhitePaint.setStrokeWidth(10);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        for (int i = 0; i < viewWidth; i++) {
            // y = Aes * sin( wx + b) + h ; Aes： 浪高； w：周期；b：初相；
            //画第一条波浪
            double radius1 = 2 * Math.PI / viewWidth * (viewWidth - i) + mOffset1;
            double sin1 = Math.sin(radius1);
            //Y轴线向右上方倾斜,高度为区域高度
            float endY = (float) (waveHeight * sin1 + (viewHeight - waveHeight - i / 5));
            canvas.drawLine(i, 0, i, endY, mWavePaint);

            //画第二条波浪
            double radius = 2 * Math.PI / viewWidth * (viewWidth - i) + mOffset2;
            double sin2 = Math.sin(radius);
            float endY2 = (float) ((waveHeight) * sin2 + (viewHeight - waveHeight - i / 5));
            canvas.drawLine(i, 0, i, endY2, mWavePaint);

            //画第三条波浪
            double radius3 = 2 * Math.PI / viewWidth * (viewWidth - i) + mOffset3;
            double getSin3 = Math.sin(radius3);
            float endY3 = (float) (waveHeight * getSin3 + (viewHeight - waveHeight - i / 5));
            canvas.drawLine(i, 0, i, endY3, mWavePaint);
            //画第四条波浪
//            double radius4 = 2 * Math.PI / viewWidth * (viewWidth - i) + mOffset4;
//            double sin4 = Math.sin(radius4);
//            float endY4 = (float) (waveHeight / 2 * sin4 + (viewHeight - waveHeight / 2 - i / 5));
//            canvas.drawLine(i, viewHeight, i, endY4, mWhitePaint);
        }

        if (mOffset1 > Float.MAX_VALUE - 1) {//防止数值超过浮点型的最大值
            mOffset1 = 0;
        }
        mOffset1 += mSpeed1;

        if (mOffset2 > Float.MAX_VALUE - 1) {//防止数值超过浮点型的最大值
            mOffset2 = 0;
        }
        mOffset2 += mSpeed2;

        if (mOffset3 > Float.MAX_VALUE - 1) {//防止数值超过浮点型的最大值
            mOffset3 = 0;
        }
        mOffset3 += mSpeed3;

        if (mOffset4 > Float.MAX_VALUE - 1) {//防止数值超过浮点型的最大值
            mOffset4 = 0;
        }
        mOffset4 += mSpeed4;

        //刷新
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        viewWidth = getDefaultSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);
    }
}