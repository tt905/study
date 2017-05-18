package com.mo.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * aidl 使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ClientLocalService mLocalService;
    Messenger mMessenger = null; //接收消息用

    private TextView tvNumber;
    private TextView tvLog2;

    boolean localBind = false;
    boolean remoteBind = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        tvNumber = (TextView) findViewById(R.id.textView);
        tvLog2 = (TextView) findViewById(R.id.textView2);
    }


    /**
     * 绑定远程服务
     */
    private void bindRemote() {
        Intent intent = new Intent("com.mo.aidl");
        intent.setPackage("com.mo.aidlservice");
        bindService(intent, conn2, Context.BIND_AUTO_CREATE);
    }

    /**
     * 检测本地服务连接状态
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("debug", "连接上服务。。。");
            localBind = true;
            mLocalService = ((ClientLocalService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "与服务断开连接。。。");
            localBind = false;
        }
    };

    /**
     * 检测远程服务连接状态
     */
    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("debug", "连接上远程服务。。。");
            remoteBind = true;
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "与远程服务断开连接。。。");
            remoteBind = false;
        }
    };

    Messenger receiverMessenger = new Messenger( new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String text = "what: " + msg.what + "\narg1: " + msg.arg1 + "\narg2: " + msg.arg2;
                    Log.d("debug", "arg1: " + msg.arg1 + "arg2: " + msg.arg2);
                    tvLog2.setText(text);
                    break;
            }

        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBind) {
            unbindService(conn);
        }
        if (remoteBind) {
            unbindService(conn2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                // 绑定本地服务
                if (!localBind) {
                    bindService(new Intent(this, ClientLocalService.class), conn, Context.BIND_AUTO_CREATE);
                    return;
                }

                int number = mLocalService.getRandomNumber();
                tvNumber.setText("random: " + number);
                break;
            case R.id.button2:
                //先绑定
                if (!remoteBind){
                    bindRemote();
                    return;
                }

                Message msg = Message.obtain(null, 1, 2, 3);
                try {
                    msg.replyTo = receiverMessenger;
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button3:
                startActivity(new Intent(MainActivity.this, AIDLActivity.class));
                break;
        }
    }
}
