package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mo.study.R;
import com.mo.study.widget.LoadingDialog;

/**
 * loading activity
 * Created by motw on 2016/12/19.
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        findViewById(R.id.btn_show_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new LoadingDialog(LoadingActivity.this).show();
                LoadingDialog.show(LoadingActivity.this, "正在登陆");
            }
        });
    }


}
