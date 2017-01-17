package com.mo.study.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 有回弹效果的layout
 * Created by motw on 2016/12/30.
 */
public class BounceableRelativeLayout  extends RelativeLayout {

    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public BounceableRelativeLayout(Context context) {
        this(context, null);
    }

    public BounceableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP :
                reset(0, 0);
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1,MotionEvent e2,float distanceX,float distanceY){
            int disY = (int) ((distanceY - 0.5) / 2);
//            beginScroll(0, disY);
            scrollBy(0, disY);
            return false;
        }

        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float x,float y) {
            return false;
        }

    }

    protected void reset(int x, int y) {
//        int dx = x - mScroller.getFinalX();
//        int dy = y - mScroller.getFinalY();
        int dx = -getScrollX();
        int dy = -getScrollY();
//        beginScroll(dx, dy);
        mScroller.startScroll(0, -dy, 0, dy); //滚动回原来的位置
        invalidate();
    }

    protected void beginScroll(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
