package com.mo.study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 十字真言
 * Created by motw on 2016/12/13.
 */
public class TenTrueWords extends View {

    private int width, height;

    private Map<Integer, Path> pathMap;
    private final int maxFontCount = 10; // 最大10个输入
    private int index;
    private final Matrix matrix;
    private Paint mPaint;

    /**   用户是否还在输入   */
    private long upTime;


    public TenTrueWords(Context context) {
        this(context, null);
    }

    public TenTrueWords(Context context, AttributeSet attrs) {
        super(context, attrs);
        pathMap = new HashMap<>();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLUE);

        matrix = new Matrix();
        matrix.setScale(0.33f, 0.33f);

        for (int i = 0; i< maxFontCount; i++){
            pathMap.put(i, new Path());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        //不能超出最大数量
        if (index >= pathMap.size()){
            return false;
        }

        Path p = pathMap.get(index);
        if (p == null){
            p = new Path();
        }


        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                p.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                p.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                postDelayed(task, 2200);
                break;
        }
        invalidate();
        return true;
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if ((System.currentTimeMillis() - upTime) >= 2000){
                Path path = pathMap.get(index);
                path.transform(matrix);
                postInvalidate();
                //到这里就是一次输入的结束
                index ++;
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出path
        for (Path p : pathMap.values()) {
            canvas.drawPath(p, mPaint);
        }
    }
}
