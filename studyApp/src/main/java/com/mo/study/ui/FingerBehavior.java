package com.mo.study.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.mo.study.widget.FingerView;

/**
 * Created by motw on 2016/12/1.
 */

public class FingerBehavior extends CoordinatorLayout.Behavior<Button> {

    private int width;


    public FingerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        return dependency instanceof FingerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {

        //根据dependency的位置，设置Button的位置

        int top = dependency.getTop();
        int left = dependency.getLeft();

        int x = width - left - child.getWidth();
        int y = top;

        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        child.setLayoutParams(layoutParams);

        return true;

    }
}
