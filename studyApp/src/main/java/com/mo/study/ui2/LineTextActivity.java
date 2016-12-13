package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mo.study.R;

/**
 * 日期选择
 * Created by motw on 2016/12/5.
 */
public class LineTextActivity extends AppCompatActivity {


    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

    }

}
