package com.mo.study.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mo.study.R;
import com.mo.study.utils.Utils;

/**
 * 模仿material design 的圈圈
 * Created by motw on 2016/12/22.
 */
public class LoadingBar extends View {


    private int mHeight;
    private int mWidth;

    private Paint mPaint;
    private final Paint transparentPaint;

    private int arcColor; //颜色
    private final int arcSize;

    private RectF mRectf;

    public LoadingBar(Context context) {
        this(context, null);
    }

    public LoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMinimumHeight(Utils.dpToPx(32, getResources()));
        setMinimumWidth(Utils.dpToPx(32, getResources()));

        int defaultSize = Utils.dpToPx(4, getResources());
        int defaultColor = Color.parseColor("#1E88E5");

        //从xml的属性中获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingBar);
        int color = typedArray.getColor(R.styleable.LoadingBar_bar_color, defaultColor);
        arcSize = typedArray.getDimensionPixelSize(R.styleable.LoadingBar_bar_size, defaultSize);
        typedArray.recycle();

        arcColor = color;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(arcColor);
        mPaint.setStrokeWidth(arcSize);

        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        int size = Math.min(w, h);
        mRectf = new RectF(arcSize, arcSize, size - arcSize, size - arcSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSecondAnimation(canvas);
        invalidate();
    }

    float sweepAngle = 1;
    float startAngle = 0;
    float rotateAngle = 0;
    float limite = 0;

    /**
     * 圆弧动画，圆弧先增加，然后在减少，增加，不停重复。 在这过程中，不停的旋转画布。
     */
    private void drawSecondAnimation(Canvas canvas){
        if (startAngle == limite) {
            sweepAngle += 6;
        }
        if (sweepAngle >= 290 || startAngle > limite) {
            startAngle += 6;
            sweepAngle -= 6;
        }
        if (startAngle > limite + 290) {
            if (startAngle> 360){
                startAngle = startAngle % 360;  //不要无限增大
            }

            limite = startAngle;
            startAngle = limite;// 从Limite处 重新开始增加,这样才能连接起来
            sweepAngle = 1;
        }
        rotateAngle += 4;//旋转角度
        if (rotateAngle > 360){
            rotateAngle = rotateAngle % 360;
        }
        canvas.rotate(rotateAngle, mWidth / 2f, mHeight / 2f);

        //画出圆弧
        canvas.drawArc(mRectf, startAngle, sweepAngle, false, mPaint);
        Log.d("debug", "startAngle  " + startAngle + "sweepAngle " + sweepAngle + "rotateAngle " + rotateAngle + "limite " + limite);
    }
}
