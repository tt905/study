package com.mo.study;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

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
        }, 1800);
    }

    private void start() {

        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted){
                            Intent start = new Intent(WelcomeActivity.this, MainActivity2.class);
                            startActivity(start);
                            WelcomeActivity.this.finish();
                        }else{
                            //没有权限。啥也不做
                        }
                    }
                });

    }


}
