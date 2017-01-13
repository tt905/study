package com.mo.study.ui2;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.mo.study.R;
import com.mo.study.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.mo.study.ui2.CardLayoutManager.MAX_SHOW_COUNT;
import static com.mo.study.ui2.CardLayoutManager.SCALE_GAP;
import static com.mo.study.ui2.CardLayoutManager.TRANS_Y_GAP;

/**
 * 卡片式切换 像人人网影视 <br>
 * 参考 http://blog.csdn.net/zxt0601/article/details/53730908
 * Created by motw on 2016/12/16.
 */
public class CardActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    private List<String> mList;
    private CardAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_test);
        recyclerView.setLayoutManager(new CardLayoutManager());
        mList = getList();
        adapter = new CardAdapter(mList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //实现循环的要点 条用这个方法的时候是手指提起了
                String remove = mList.remove(viewHolder.getLayoutPosition());
                mList.add(0, remove);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Log.d("xdebug", "onChildDraw");
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                //先根据滑动的dxdy 算出现在动画的比例系数fraction
                double swipValue = Math.sqrt(dX * dX + dY * dY);// 手指滑动的距离
                double fraction = swipValue / ( recyclerView.getWidth() * getSwipeThreshold(viewHolder) ); // 与阈值的百分比 接下来会根据百分比 计算缩放的大小

                //边界修正 最大为1
                if (fraction > 1) {
                    fraction = 1;
                }
                //对每个ChildView进行缩放 位移
                int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);
                    //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
                    int level = childCount - i - 1; // -1 是因为第0层(也就是顶层)是不变的
                    if (level > 0) {
                        child.setScaleX((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));

                        if (level < MAX_SHOW_COUNT - 1) {
                            // 1 - SCALE_GAP * level 是原来的倍数 现在在原来的倍数上加 fraction * SCALE_GAP 最大为 SCALE_GAP
                            child.setScaleY((float) (1 - SCALE_GAP * level + fraction * SCALE_GAP));
                            child.setTranslationY((float) (TRANS_Y_GAP * level - fraction * TRANS_Y_GAP));
                        }
                    }
                }
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i= 0;i <10; i++){
            list.add("string" + i);
        }
        return list;
    }


}
