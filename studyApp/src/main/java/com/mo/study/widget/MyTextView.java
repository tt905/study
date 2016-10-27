package com.mo.study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *  Created by motw on 2016/10/24.
 */

public class MyTextView extends View {
    private Paint mPaint;
    private Paint linePaint;
    private float mWidth;
    private float mHeight;

    private int baseX, baseY;// Baseline绘制的XY坐标

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(70);
        mPaint.setColor(Color.BLACK);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawcoor(canvas);

        String ss = "sdyght";

        // 计算Baseline绘制的起点X轴坐标
        baseX = (int) (canvas.getWidth() / 2 - mPaint.measureText(ss) / 2);

        // 计算Baseline绘制的Y坐标
        baseY = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));

        canvas.drawText(ss, baseX, baseY, mPaint);
        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, linePaint);
    }

    private void drawcoor(Canvas canvas) {
//        canvas.save();
        canvas.translate(mWidth/2, mHeight/2);
        //绘制坐标原点
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        canvas.drawPoint(0, 0, mPaint);

        mPaint.setStrokeWidth(1);
        //绘制X轴
        canvas.drawLine(-mWidth / 2 * 0.8f, 0, mWidth / 2 * 0.8f, 0, mPaint);
        //绘制Y轴
        canvas.drawLine(0, -mHeight / 2 * 0.8f, 0, mHeight / 2 * 0.8f, mPaint);
//        canvas.restore();
    }
}
