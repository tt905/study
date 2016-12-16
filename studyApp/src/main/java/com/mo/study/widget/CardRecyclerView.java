package com.mo.study.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by motw on 2016/12/16.
 */
public class CardRecyclerView extends RecyclerView {


    private float mTouchDownX;
    private float mTouchDownY;
    private float mTopViewX;
    private float mTopViewY;

    public CardRecyclerView(Context context) {
        this(context, null);
    }

    public CardRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CardRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (getChildCount() == 0) {
            return super.onTouchEvent(e);
        }

        View topView = getChildAt(getChildCount() - 1);
        float touchX = e.getX();
        float touchY = e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = touchX;
                mTouchDownY = touchY;
                mTopViewX = topView.getX();
                mTopViewY = topView.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = touchX - mTouchDownX;
                float dy = touchY - mTouchDownY;
                topView.setX(mTopViewX + dx);
                topView.setY(mTopViewY + dy);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onTouchEvent(e);
    }
}
