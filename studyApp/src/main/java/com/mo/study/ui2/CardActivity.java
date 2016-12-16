package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mo.study.R;
import com.mo.study.adapter.CardAdapter;
import com.mo.study.widget.CardRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片式切换
 * Created by motw on 2016/12/16.
 */
public class CardActivity extends AppCompatActivity {


    private CardRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        recyclerView = (CardRecyclerView) findViewById(R.id.recycler_test);
        recyclerView.setLayoutManager(new CardLayoutManager());
        CardAdapter adapter = new CardAdapter(getList());
        recyclerView.setAdapter(adapter);
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i= 0;i <5; i++){
            list.add("string" + i);
        }
        return list;
    }


}
