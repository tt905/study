package com.mo.study.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.mo.study.R;

/**
 * 画图（展开图画）
 * Created by motw on 2016/11/3.
 */

public class PView extends View {

    // 1.创建Picture
    private Picture mPicture = new Picture();
    private Bitmap bitmap;
    private int bWidth;
    private int bHeight;
//    private Paint mPaint;

    private Rect src;
    private Rect dst;

    private ValueAnimator animator;

    public PView(Context context) {
        this(context, null);
    }

    public PView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        recording();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cat);
        bHeight = bitmap.getHeight();
        bWidth = bitmap.getWidth();
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        src = new Rect(0, 0, 10 , bHeight);
        dst = new Rect(0, 0, 10, bHeight);

        animator = ValueAnimator.ofInt(10, bWidth)
                .setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               int value = (int) animation.getAnimatedValue();
                src = new Rect(0,0, value, bHeight);
                dst = new Rect(0, 0, value, bHeight);
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, src, dst, null);
    }


    private void recording() {
        Canvas canvas = mPicture.beginRecording(500, 500);
        canvas.translate(250, 250);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, 100, p);
        mPicture.endRecording();
    }
}
