package com.mo.study.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mo.study.R;

/**
 *  Created by motw on 2016/11/14.
 */
public class SpeedView extends View {

    private int speedLevel = 7;     //速度可调节的节点数

    private Paint linePaint, imgPaint;

    private int mWidth;
    private int mHeight;

    private Bitmap iconSlowSpeed;

    public SpeedView(Context context) {
        this(context, null);
    }

    public SpeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFA2A2A2);//设置默认颜色
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);

        imgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        iconSlowSpeed = BitmapFactory.decodeResource(getResources(), R.drawable.slow_speed);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(-mWidth / 2 * 0.95f, 0, mWidth / 2 * 0.95f, 0, linePaint);

        drawSpeedLevel(canvas);
    }

    private void drawSpeedLevel(Canvas canvas) {

//        canvas.drawBitmap();
    }
}
