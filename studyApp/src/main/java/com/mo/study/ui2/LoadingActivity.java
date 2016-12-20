package com.mo.study.ui2;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.mo.study.R;

/**
 * Created by motw on 2016/12/19.
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        AppCompatImageView animView = (AppCompatImageView) findViewById(R.id.anim);
        Drawable animation = animView.getDrawable();
        if (animation instanceof Animatable) {
            ((Animatable) animation).start();
        }
    }


}
