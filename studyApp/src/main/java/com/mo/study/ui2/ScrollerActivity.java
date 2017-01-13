package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mo.study.R;

/**
 *
 * Created by motw on 2016/12/30.
 */
public class ScrollerActivity extends AppCompatActivity {


    private LinearLayout layout;
    private Button scrollToBtn;
    private Button scrollByBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);


    }
}
