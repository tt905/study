package com.mo.study.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mo.study.R;
import com.mo.study.adapter.ExampleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView自动加载更多
 * Created by motw on 2016/10/27.
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearManager;

    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private ExampleAdapter adapter;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_test);
        linearManager =  new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.d("debug", "SCROLL_STATE_IDLE");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.d("debug", "SCROLL_STATE_DRAGGING");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.d("debug", "SCROLL_STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearManager.getItemCount();
                lastVisibleItem = linearManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + 5)) {
                    onLoadMore();
                    isLoading = true;
                }
            }
        });

        list = getData();
        adapter = new ExampleAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    private void onLoadMore() {
        list.add(null);
        adapter.notifyItemInserted(list.size() - 1);

        //模拟耗时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Remove loading item
                list.remove(list.size() - 1);
                adapter.notifyItemRemoved(list.size());

                //Load data
                int index = list.size();
                int end = index + 20;
                for (int i = index; i < end; i++) {
                    list.add("增加" + i);
                }
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 5000);
    }

    private List<String> getData() {
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result,getResources().getStringArray(R.array.test_data));
        return result;
    }
}
