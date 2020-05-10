package com.luck.cloud.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.luck.cloud.utils.view.ViewUtil;


/**
 * Created by liuyin on 2019/4/8 11:32
 * Describe: 圆环统计图
 */
public class PieChartView extends View {

    private Paint mTextP;//中间字
    private double mSweepAnglePer;
    private BarAnimation anim;
    private int stepnumbermax = 100;// 默认最大数
    private double finished;
    private Paint progressPaint;//进度条
    private Paint backgroundPaint;//圆环背景
    private float interpolatedTime;

    private int lengthSide;//边长

    private int annulusWidth = 35;//底部圆的宽度

    //进度条需要渐变
    private static final int[] SECTION_COLORS = {Color.parseColor("#4BAFFE"), Color.parseColor("#28F1B0")};

    public PieChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {


        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setColor(Color.parseColor("#0466eb"));
        backgroundPaint.setStrokeWidth(annulusWidth);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置两头为圆弧状
        progressPaint.setColor(Color.parseColor("#3fc7e2"));
        progressPaint.setStrokeWidth((float) (annulusWidth / 3.5));

        mTextP = new Paint();
        mTextP.setAntiAlias(true);
        mTextP.setColor(Color.parseColor("#44b0ff"));
        mTextP.setTextSize(ViewUtil.dp2px(27));
        anim = new BarAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置圆环边框属性
        RectF oval = new RectF(annulusWidth / 2, annulusWidth / 2, lengthSide - annulusWidth / 2, lengthSide - annulusWidth / 2);
        //画背景圆环
        canvas.drawArc(oval, 0, 360, false,
                backgroundPaint);

        //画进度圆环
        canvas.drawArc(oval, -90, (float) mSweepAnglePer, false,
                progressPaint);
        double progress = interpolatedTime * finished;
        //进度条加载完后设置进度条渐变
        if (interpolatedTime==1){
            LinearGradient shader=getPaintGradient();
            progressPaint.setShader(shader);

        }
        //居中显示字体

        progress = (double) Math.round(progress * 100) / 100;
        String mText = doubleTrans(progress) + "%";
        Rect rect = new Rect();
        mTextP.getTextBounds(mText, 0, mText.length(), rect);
        int width = rect.width();
        int height = rect.height();
        canvas.drawText(mText, lengthSide / 2 - width / 2, lengthSide / 2 + height / 2, mTextP);


    }

    public static String doubleTrans(double d){
        if(Math.round(d)-d==0){
            return String.valueOf((long)d);
        }
        return String.valueOf(d);
    }

    /**
     * 获取进度条渐变属性
     * @return
     */
    private LinearGradient getPaintGradient(){
        int[] colors = new int[SECTION_COLORS.length];
        System.arraycopy(SECTION_COLORS, 0, colors, 0, SECTION_COLORS.length);
        if (mSweepAnglePer <= 180) {
            //如果旋转角度小于180度,将渐变方向设置为从0度角向180度方向渐变
            LinearGradient  shader = new LinearGradient(lengthSide / 2, 0, lengthSide / 2, (float) (lengthSide*(mSweepAnglePer*2/360)), colors, null,
                    Shader.TileMode.MIRROR);
            return shader;
        } else {
            //如果角度大于18度,将渐变方向设置为从45度角向135度方向渐变
            LinearGradient   shader = new LinearGradient(lengthSide, 0, (float) (lengthSide
                                * ((360-mSweepAnglePer)/360)), lengthSide, colors, null,
                    Shader.TileMode.MIRROR);
            return shader;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形

        lengthSide = min;


    }

    /**
     * 进度条动画
     *
     */
    public class BarAnimation extends Animation {
        public BarAnimation() {

        }

        /**
         * 每次系统调用这个方法时， 改变mSweepAnglePer，mPercent，stepnumbernow的值，
         * 然后调用postInvalidate()不停的绘制view。
         */
        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            PieChartView.this.interpolatedTime = interpolatedTime;
            if (interpolatedTime < 1.0f) {
                if (stepnumbermax == 0) {
                    mSweepAnglePer = 0;
                } else {
                    mSweepAnglePer = interpolatedTime * finished * 360
                            / stepnumbermax;
                }
            } else {
                if (stepnumbermax == 0) {
                    mSweepAnglePer = 0;
                } else {
                    mSweepAnglePer = finished * 360 / stepnumbermax;
                }
            }
            postInvalidate();
        }
    }


    /**
     * 更新步数和设置一圈动画时间
     *
     * @param stepnumber
     * @param time
     */
    public void update(double finshed, int stepnumber, int time) {
        this.finished = finshed;
        anim.setDuration(time);
        this.startAnimation(anim);

    }

    /**
     * 设置每天的最大步数
     *
     * @param Maxstepnumber
     */
    public void setMaxstepnumber(int Maxstepnumber) {
        stepnumbermax = Maxstepnumber;
    }



}
