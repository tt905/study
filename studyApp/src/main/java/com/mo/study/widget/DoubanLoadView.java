package com.mo.study.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 豆瓣loadding
 * Created by motw on 2016/10/18.
 */

public class DoubanLoadView extends View {
    private String TAG = this.getClass().getSimpleName();

    private Paint mPaint;

    //宽高
    private int mWidth;
    private int mHeight;

    private ValueAnimator animator;
    private float animatedValue;
    private long animatorDuration = 5000;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();

    private float [] points;


    public DoubanLoadView(Context context) {
        this(context, null);
    }

    public DoubanLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubanLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        initAnimator(3000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        points = new float[]{
                0,0,
                (mWidth / 2 * 0.8f), 0
                , 0, (mHeight / 2 * 0.8f)
                , (-mWidth / 2 * 0.8f), 0
                , 0, (-mHeight / 2 * 0.8f)
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setColor(Color.GREEN);
//        mPaint.setStrokeWidth(10);
        canvas.translate(mWidth/2, mHeight/2);
        doubanAnimator(canvas, mPaint);
    }

    private void initAnimator(long duration){
        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator=ValueAnimator.ofFloat(0,855).setDuration(duration);
            animator.setInterpolator(timeInterpolator);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    private void doubanAnimator(Canvas canvas, Paint mPaint){
        mPaint.setStyle(Paint.Style.STROKE);//描边
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角笔触
        mPaint.setColor(Color.rgb(97, 195, 109));
        mPaint.setStrokeWidth(15);
        float point = Math.min(mWidth, mHeight)*0.06f/2;
        float r = point*(float) Math.sqrt(2);
        RectF rectF = new RectF(-r,-r,r,r);

//        canvas.drawArc(rectF,startAngle,sweepAngle,false,mPaint);

        // draw eye
        canvas.drawPoints(new float[]{
                -point,-point
                ,point,-point
        },mPaint);

//        canvas.restore();
    }

    private void log(String msg){
        Log.d(TAG, msg);
    }
}
