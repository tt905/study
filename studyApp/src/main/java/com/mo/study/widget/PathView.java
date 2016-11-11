package com.mo.study.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mo.study.R;

/**
 *  pathPolygon study
 *  Created by motw on 2016/11/9.
 */
public class PathView extends View {

    private int mCount = 6;     //变数
    private float angle;
    private float mRadius;       //半径

    private Paint mPaint;       //蛛网画笔
    private Paint mTextPaint;   //文字画笔
    private Paint valuePaint;               //数据区画笔

    private int mWidth;
    private int mHeight;

    private int centerX;
    private int centerY;
    private String[] titles = {"江局","蔡生","yellow","k哥","小明","小强"};
    private float[] data = {100,60,70,40,90,100}; //各维度分值
    private float maxValue = 100;             //数据最大值


    private Path pathPolygon;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);
        int textSize = getResources().getDimensionPixelSize(R.dimen.fontDefault);
        mTextPaint.setTextSize(textSize);
//        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setColor(Color.BLUE);

        pathPolygon = new Path();
        angle = (float) (Math.PI * 2 / mCount); //使用的是弧度。
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(h, w)/2*0.75f;
        mWidth = w;
        mHeight = h;
        centerX = mWidth/2;
        centerY = mHeight/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
        canvas.restore();
    }

    /**
     * 画蜘蛛网
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        float r = mRadius / (mCount - 1);//r是蜘蛛丝之间的间距
        pathPolygon.reset();
        for (int i = 1; i < mCount; i++) {
            float currentR = r * i;

            for (int j = 0; j < mCount; j++) {
                //按顺时针方向画
                if (j == 0) {
                    pathPolygon.moveTo(currentR + 0, 0);
                } else {
                    float x = (float) (0 + Math.cos(angle * j) * currentR);
                    float y = (float) (0 + Math.sin(angle * j) * currentR);
                    pathPolygon.lineTo(x, y);
                }
            }

            pathPolygon.close();          //闭合曲线

        }
        canvas.drawPath(pathPolygon, mPaint);
    }

    /**
     * 画对角线
     * @param canvas
     */
    private void drawLines(Canvas canvas){

        pathPolygon.reset();
        for (int i=0; i< mCount; i++){
            pathPolygon.moveTo(0,0);
            float x = (float) (Math.cos(angle * i) * mRadius);
            float y = (float) (Math.sin(angle * i) * mRadius);
            pathPolygon.lineTo(x, y);
        }
        canvas.drawPath(pathPolygon, mPaint);
    }

    /**
     * 画出文字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < mCount; i++) {
            float x = (float) (Math.cos(angle * i) * (mRadius + fontHeight));
            float y = (float) (Math.sin(angle * i) * (mRadius + fontHeight));
            if (x < -10){
                x = x - mTextPaint.measureText(titles[i]);
            }
            canvas.drawText(titles[i], x, y, mTextPaint);
        }

        String ss = "财富水平";
        canvas.drawText(ss, -mTextPaint.measureText(ss) / 2, mRadius * 1.3f, mTextPaint);
    }

    /**
     * 画出覆盖区域
     */
    private void drawRegion(Canvas canvas){
        pathPolygon.reset();
        valuePaint.setAlpha(255);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < mCount; i++) {
            float percent = data[i]/maxValue;
            float x = (float) (Math.cos(angle * i) * mRadius);
            float y = (float) (Math.sin(angle * i) * mRadius);
            x = x*percent;
            y = y*percent;
            if(i==0){
                //移到第一个点位置
                pathPolygon.moveTo(x, y);
            }else{
                //画线
                pathPolygon.lineTo(x,y);
            }
            //绘制小圆点
            canvas.drawCircle(x,y,10,valuePaint);

        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(pathPolygon, valuePaint);

        //绘制填充区域
        valuePaint.setAlpha(127);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(pathPolygon, valuePaint);
    }

}
