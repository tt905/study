package com.mo.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 2800);
    }

    private void start() {

        Intent start = new Intent(this, MainActivity2.class);
        startActivity(start);
        this.finish();
    }


}
