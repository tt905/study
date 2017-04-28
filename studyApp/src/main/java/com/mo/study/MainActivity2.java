package com.mo.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mo.study.network.VolleyActivity;
import com.mo.study.ui.RecyclerViewActivity2;
import com.mo.study.ui2.BitmapActivity;
import com.mo.study.ui2.CardActivity;
import com.mo.study.ui2.ConstraintLayoutActivity;
import com.mo.study.ui2.CoordinatorActivity2;
import com.mo.study.ui2.DialogActivity;
import com.mo.study.ui2.LineTextActivity;
import com.mo.study.ui2.LoadingActivity;
import com.mo.study.ui2.PopupActivity;
import com.mo.study.ui2.PositionActivity;
import com.mo.study.ui2.ScrollerActivity;
import com.mo.study.ui2.SpinnerActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

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
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn020).setOnClickListener(this);
        findViewById(R.id.btn021).setOnClickListener(this);
        findViewById(R.id.btn022).setOnClickListener(this);
        findViewById(R.id.btn023).setOnClickListener(this);
        findViewById(R.id.btn024).setOnClickListener(this);
        findViewById(R.id.btn025).setOnClickListener(this);


    }

    private void addShortcut(String name) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        // 名字
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(MainActivity2.this, R.mipmap.ic_launcher));

        // 设置关联程序
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(this, MainActivity2.class);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        sendBroadcast(addShortcutIntent);
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
            case R.id.btn7:
                intent.setClass(this, ConstraintLayoutActivity.class);
                break;
            case R.id.btn8:
                addShortcut("学习app");
                return;
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
            case R.id.btn024:
                intent.setClass(this, PopupActivity.class);
                break;
            case R.id.btn025:
                intent.setClass(this, PositionActivity.class);
                break;
        }
        startActivity(intent);
    }
}
