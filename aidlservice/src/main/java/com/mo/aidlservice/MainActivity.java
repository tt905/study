package com.mo.aidlservice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvLog;
    boolean bindLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLog = (TextView) findViewById(R.id.tvLog);

        //绑定本地服务
//        Intent intent = new Intent(this, MessengerService.class);
//        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        tvLog.setText("pid: " + Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindLocal) {
            unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bindLocal = true;
            Messenger messenger = new Messenger(service);
            Message msg = Message.obtain(null, 2);
            msg.replyTo = receiverMessenger;
            // 发送一个消息，让后台service记住自己replyTo
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            tvLog.setText("已绑定");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindLocal = false;
        }
    };

    Messenger receiverMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text;
            text = "what: " + msg.what + "\narg1: " + msg.arg1 + "\narg2: " + msg.arg2;
            tvLog.setText(text);
        }
    });
}
