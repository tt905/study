package com.mo.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mo.study.ui.PActivity;
import com.mo.study.ui.PieActivity;
import com.mo.study.ui.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()){

            case R.id.btn1:
                intent.setClass(this, RecyclerViewActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this, PieActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this, PActivity.class);
                break;
        }
        startActivity(intent);
    }
}
