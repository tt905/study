package com.mo.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * 跨进程的通信
 */
public class MessengerService extends Service {


    private static final int MSG_SAY_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msgFrom) {
            Log.d("debug", "接收到客户端消息 what: " + msgFrom.what );
            switch (msgFrom.what) {
                case MSG_SAY_HELLO:

//                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    Message msgSend = Message.obtain(msgFrom);
                    msgSend.what = MSG_SAY_HELLO;
                    try {
                        Thread.sleep(1000);
                        msgSend.arg2 = msgFrom.arg1 + msgFrom.arg2;
                        msgFrom.replyTo.send(msgSend);
                        if (localMessenger != null){
                            localMessenger.send(msgSend);
                        }
                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }
                case 2:
                    localMessenger = msgFrom.replyTo;
                    break;

                default:
                    super.handleMessage(msgFrom);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    Messenger localMessenger = null;

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }


}
