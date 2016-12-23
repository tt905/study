package com.mo.study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by motw on 2016/12/22.
 */
public class TestView extends View {

    private Paint mPaint;

    private int mHeight;
    private int mWidth;
    private RectF mRectf;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFFA4C739);
        mPaint.setStrokeWidth(8);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        int size = Math.min(w, h);
        mRectf = new RectF(0, 0, size * 0.8f , size * 0.8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectf, 0, 90, false, mPaint);
    }
}
