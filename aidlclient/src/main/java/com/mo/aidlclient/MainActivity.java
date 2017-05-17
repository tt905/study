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
public class MainActivity extends AppCompatActivity {

    ClientService mService;
    Messenger mMessenger = null;
    private TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bindService(new Intent(this, ClientService.class), conn, Service.BIND_AUTO_CREATE);
        Intent intent = new Intent("com.mo.aidl");
        intent.setPackage("com.mo.aidlservice");
        bindService(intent, conn2, Context.BIND_AUTO_CREATE);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = mService.getRandomNumber();
                tvNumber.setText("random: " + number);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain(null, 1, 2, 3);
                try {
                    msg.replyTo = receiverMessenger;
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvNumber = (TextView) findViewById(R.id.textView);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("debug", "连接上服务。。。");
            mService = ((ClientService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "与服务断开连接。。。");
        }
    };

    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("debug", "连接上服务。。。");
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "与服务断开连接。。。");
        }
    };

    Messenger receiverMessenger = new Messenger( new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.d("debug", "arg1: " + msg.arg1 + "arg2: " + msg.arg2);
                    break;
            }

        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(conn);
        unbindService(conn2);
    }
}
