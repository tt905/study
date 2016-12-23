package com.mo.study.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.mo.study.utils.Utils;

/**
 * modify from MaterialDesignLibrary
 * <a href="https://github.com/navasmdc/MaterialDesignLibrary">https://github.com/navasmdc/MaterialDesignLibrary</a> <br/>
 * Created by motw on 2016/12/22.
 */
public class MDProgressBar extends View {
    private final Paint transparentPaint;
    private Paint mPaint;
    private Paint mPaint2;

    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    int backgroundColor = Color.parseColor("#1E88E5");


    public MDProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(makePressColor());

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(backgroundColor);

        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    // Set atributtes of XML to View
    protected void setAttributes(AttributeSet attrs){

        setMinimumHeight(Utils.dpToPx(32, getResources()));
        setMinimumWidth(Utils.dpToPx(32, getResources()));

        //设置背景色
        // Color by resource
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
        if(bacgroundColor != -1){
            setBackgroundColor(getResources().getColor(bacgroundColor));
        }else{
            // Color by hexadecimal
            int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (background != -1)
                setBackgroundColor(background);
            else
                setBackgroundColor(Color.parseColor("#1E88E5"));
        }

        setMinimumHeight(Utils.dpToPx(3, getResources()));
    }

    /**
     * Make a dark color to ripple effect
     * @return
     */
    protected int makePressColor(){
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor >> 0) & 0xFF;
        return Color.argb(128,r, g, b);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!firstAnimationOver)
            drawFirstAnimation(canvas);
        if(cont > 0)
            drawSecondAnimation(canvas);
        invalidate();
    }

    float radius1 = 0;
    float radius2 = 0;
    int cont = 0;
    boolean firstAnimationOver = false;


    /**
     * Draw first animation of view
     * @param canvas
     */
    private void drawFirstAnimation(Canvas canvas){
        int width = getWidth();
        int height = getHeight();

        if (radius1 < width / 2) {
            radius1 = (radius1 >= width / 2) ? (float) width / 2 : radius1 + 2f;
            canvas.drawCircle(width / 2, height / 2, radius1, mPaint);
        } else {
            //第二个动画，先画一个圆，半径是高的一半，再从中间画一个透明的圆，慢慢放大
            Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas temp = new Canvas(bitmap);

            temp.drawCircle(width / 2, height / 2, height / 2, mPaint);

            if (cont >= 50) {
                //3
                radius2 = (radius2 >= width / 2) ? (float) width / 2 : radius2 + 2.2f;
            } else {
                // 1
                radius2 = (radius2 >= width / 2 - Utils.dpToPx(4, getResources())) ? (float) width / 2 - Utils.dpToPx(4, getResources()) : radius2 + 2.2f;
            }

            temp.drawCircle(width / 2, height / 2, radius2, transparentPaint);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());

            if (radius2 >= width / 2 - Utils.dpToPx(4, getResources())) {
                //2
                cont++;
            }

            if (radius2 >= width / 2) {
                firstAnimationOver = true;
            }
        }
    }

    int sweepAngle = 1;
    int startAngle = 0;
    float rotateAngle = 0;
    int limite = 0;


    /**
     * Draw second animation of view
     * @param canvas
     */
    private void drawSecondAnimation(Canvas canvas){
        if(startAngle == limite)
            sweepAngle +=10;
        if(sweepAngle >= 290 || startAngle > limite){
            startAngle +=10;
            sweepAngle -=10;
        }
        if(startAngle > limite + 290){
            limite = startAngle;
            startAngle = limite;
            sweepAngle = 1;
        }
        rotateAngle += 10;//旋转角度
        canvas.rotate(rotateAngle,getWidth()/2, getHeight()/2);

        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);

//		temp.drawARGB(0, 0, 0, 255);
        temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), startAngle, sweepAngle, true, mPaint2); //画出圆弧
        temp.drawCircle(getWidth()/2, getHeight()/2, (getWidth()/2)-Utils.dpToPx(4, getResources()), transparentPaint); //画圆 覆盖内部

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }





    // Set color of background
    public void setBackgroundColor(int color){
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//        if(isEnabled()) {
//            beforeBackground = backgroundColor;
//        }
        this.backgroundColor = color;
    }

}
