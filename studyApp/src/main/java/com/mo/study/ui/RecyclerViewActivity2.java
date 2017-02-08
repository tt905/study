package com.mo.study.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mo.study.R;
import com.mo.study.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView 必知必会
 * Created by motw on 2017/2/7.
 */
public class RecyclerViewActivity2 extends AppCompatActivity {

    private RecyclerView mRv;
    private QuickAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview2);

        mRv = (RecyclerView) findViewById(R.id.rView2);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuickAdapter<String>(initData()) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_r2;
            }

            @Override
            public void convert(VH holder, String data, int position) {
                TextView title = holder.getView(R.id.title);
                title.setText(data);
            }
        };
        mRv.setAdapter(mAdapter);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i<18; i++){
            list.add("item  " + i);
        }
        return list;
    }
}
