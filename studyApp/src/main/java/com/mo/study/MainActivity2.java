package com.mo.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mo.study.network.VolleyActivity;
import com.mo.study.ui.RecyclerViewActivity2;
import com.mo.study.ui2.BitmapActivity;
import com.mo.study.ui2.CardActivity;
import com.mo.study.ui2.CoordinatorActivity2;
import com.mo.study.ui2.DialogActivity;
import com.mo.study.ui2.LineTextActivity;
import com.mo.study.ui2.LoadingActivity;
import com.mo.study.ui2.ScrollerActivity;
import com.mo.study.ui2.SpinnerActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
//        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn020).setOnClickListener(this);
        findViewById(R.id.btn021).setOnClickListener(this);
        findViewById(R.id.btn022).setOnClickListener(this);
        findViewById(R.id.btn023).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()){

            case R.id.btn1:
                intent.setClass(this, LineTextActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this, CardActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this, LoadingActivity.class);
                break;
            case R.id.btn4:
                intent.setClass(this, CoordinatorActivity2.class);
                break;
            case R.id.btn5:
                intent.setClass(this, ScrollerActivity.class);
                break;
            case R.id.btn6:
                intent.setClass(this, VolleyActivity.class);
                break;
//            case R.id.btn7:
//                intent.setClass(this, CoordinatorActivity.class);
//                break;
            case R.id.btn020:
                intent.setClass(this, RecyclerViewActivity2.class);
                break;
            case R.id.btn021:
                intent.setClass(this, DialogActivity.class);
                break;
            case R.id.btn022:
                intent.setClass(this, SpinnerActivity.class);
                break;
            case R.id.btn023:
                intent.setClass(this, BitmapActivity.class);
                break;
        }
        startActivity(intent);
    }
}
