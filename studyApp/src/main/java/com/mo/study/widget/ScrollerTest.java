package com.mo.study.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 *
 * Created by motw on 2016/12/30.
 */
public class ScrollerTest extends ViewGroup {

    private Scroller mScroller;

    private float lastMove;

    public ScrollerTest(Context context) {
        super(context);
    }

    public ScrollerTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i=0; i<childCount; i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(50,50, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastMove = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float move =  event.getRawX();
                float offset = lastMove - move;
                scrollBy((int) offset, 0);
                lastMove = move;
                break;
            case MotionEvent.ACTION_UP:
                mScroller.startScroll(getScrollX(), 0, -getWidth()/3, 0, 5000);
                invalidate();
                break;
        }

        return  true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
