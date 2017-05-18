package com.mo.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 默认情况，RPC调用是同步的，所以小心耗时的不要在主线程调用
 * 发生异常的话，异常是不会回传给调用方(client)的
 * Created by motw on 2017/5/18.
 */
public class AIDLService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }



    private ICalculate.Stub mBinder = new ICalculate.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }
    };
}
