package com.mo.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mo.aidlservice.ICalculate;

/**
 *
 * Created by motw on 2017/5/18.
 */
public class AIDLActivity extends AppCompatActivity {

    ICalculate mService;

    TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        tvLog = (TextView) findViewById(R.id.textView3);

        Intent intent = new Intent("com.mo.remote.aidl");
        intent.setPackage("com.mo.aidlservice");
        bindService(intent , conn , Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("debug", "连接到远程aidlservice...");
            Log.d("debug", "本地pid： " + Process.myPid());

            mService = ICalculate.Stub.asInterface(service);
            try {
                int pid = mService.getPid();
                String text = "本地进程pid：" + Process.myPid() + " 远程pid：" + pid;
                tvLog.setText(text);
                Log.d("debug", "pid: " + pid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "aidl连接断开");
        }
    };
}
