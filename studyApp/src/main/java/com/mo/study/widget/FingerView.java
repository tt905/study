package com.mo.study.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 随着手指移动
 * Created by motw on 2016/11/23.
 */

public class FingerView extends View {

    private int lastX;
    private int lastY;

    public FingerView(Context context) {
        this(context, null);
    }

    public FingerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0 );

    }

    public FingerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();

                layoutParams.leftMargin = layoutParams.leftMargin +(x-lastX);
                layoutParams.topMargin = layoutParams.topMargin + (y-lastY);
                setLayoutParams(layoutParams);
                requestLayout();
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }

        lastX = x;
        lastY = y;

        return true;
    }
}
