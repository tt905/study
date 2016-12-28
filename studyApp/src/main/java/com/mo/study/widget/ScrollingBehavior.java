package com.mo.study.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;

import com.mo.study.R;
import com.mo.study.utils.Utils;

import java.lang.ref.WeakReference;

/**
 * 有bug ，快速滑动不支持
 * Created by motw on 2016/12/20.
 */
public class ScrollingBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private boolean isExpanded = false;
    private boolean isScrolling = false;

    private WeakReference<View> dependentView;
    private Scroller scroller;
    private Handler handler;
    private Resources res;

    public ScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        handler = new Handler();
        res = context.getResources();
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if (dependency != null && dependency instanceof ImageView) {
            dependentView = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getDependentViewCollapsedHeight()));
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency){
        Resources resources = dependentView.get().getResources();
        final float progress = 1.f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height)));

        child.setTranslationY(dependency.getHeight() + dependency.getTranslationY());

        float scale = 1 + 0.4f * (1.f - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);
        dependency.setAlpha(progress);

        return true;
    }

    //  该方法决定了当前控件是否能接收到其内部View
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //该方法的会传入内部View移动的dx,dy
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        scroller.abortAnimation();
        isScrolling = false;

        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    // dx 往左是正， 往右是负
    // dy 往上的正，往下是负
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0){
            // 往上滑
            View view = getDependentView();
            float oldTranslateY = view.getTranslationY();
            float newTranslateY = oldTranslateY - dy;
            Log.d("xdebug", "oldTranslateY  " + oldTranslateY + "  newTranslateY  " + newTranslateY + " dy  " + dy);

            int height = view.getHeight();
            //大于最小高度
            if (height- Math.abs(oldTranslateY) > Utils.dpToPx(50, target.getResources())) {
                //消费掉这个滑动
                view.setTranslationY(newTranslateY);
                consumed[1]  = dy;
            }
        }else{
            //往下滑 不管,先让内部的子view消费
        }
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed < 0) {
            // 内部滚动没消费掉的滑动
            View view = getDependentView();
            float oldTranslateY = view.getTranslationY();
            if (oldTranslateY >= 0){
                return ;
            }
            float newTranslateY = oldTranslateY - dyUnconsumed;
            Log.d("xdebug", "oldTranslateY2  " + oldTranslateY + "  newTranslateY2  " + newTranslateY + " dyUnconsumed  " + dyUnconsumed);
            int height = view.getHeight();
            if (height - Math.abs(oldTranslateY) > 0) {
                view.setTranslationY(newTranslateY);
            }
        }

    }

    //你可以捕获对内部View的fling事件，如果return true则表示拦截掉内部View的事件。
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        return  false;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target) {
        if (isScrolling){
            return ;
        }
    }


    private float  getDependentViewCollapsedHeight() {
        return res.getDimension(R.dimen.collapsed_header_height); // 头部最小距离
    }

    private View getDependentView() {
        return dependentView.get();
    }

}
