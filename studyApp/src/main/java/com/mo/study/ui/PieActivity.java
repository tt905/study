package com.mo.study.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mo.study.R;
import com.mo.study.model.PieData;
import com.mo.study.widget.PieView;

import java.util.ArrayList;

/**
 *  Created by motw on 2016/11/3.
 */

public class PieActivity extends AppCompatActivity {

    int [] shuju = {50,80,33,54,88,199,10};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        // 准备数据
        ArrayList<PieData> pies = new ArrayList<>();
        for(int i=0; i<7; i++){
            PieData data = new PieData(i+"哈哈", shuju[i]);
            pies.add(data);
        }
        PieView pieView = (PieView) findViewById(R.id.pie);
        pieView.setData(pies);

    }

}
