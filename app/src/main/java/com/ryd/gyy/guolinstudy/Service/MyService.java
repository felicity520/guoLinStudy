package com.ryd.gyy.guolinstudy.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.ryd.gyy.guolinstudy.IMyInterface;

public class MyService extends Service {

    public final static String TAG = "MyService";

    /**
     * -----这是学习AIDI的代码-----
     * Stub是IMyInterface中的一个静态抽象类，继承了Binder，并且实现了IMyInterface接口
     * IMyInterface.aidl是我自己定义的接口
     * 因为实现了IMyInterface接口，所以要重写getInfor方法
     */
    private IBinder binder = new IMyInterface.Stub() {

        @Override
        public String getInfor(String s) throws RemoteException {
            Log.i(TAG, s);
            return "我是 Service 返回的字符串";
        }
    };

    public final static int SERVICEID = 0x0001;

    /**
     * 这是学习Messenger的代码
     */
    private Messenger messenger = new Messenger(new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == SERVICEID) {
                //接受从客户端传来的消息
                Log.d(TAG, "客户端传来的消息===>>>>>>");
                String str = (String) msg.getData().get("content");
                Log.d(TAG, str);

                //发送数据给客户端
                Message msgTo = Message.obtain();
                msgTo.arg1 = 0X0002;
                Bundle bundle = new Bundle();
                bundle.putString("content", "我是从服务器来的字符串");
                msgTo.setData(bundle);
                try {
                    //注意，这里把数据从服务器发出了
                    msg.replyTo.send(msgTo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    /**
     * 这是学习AIDI的代码
     *
     * @param intent
     * @return
     */
//    @Override
//    public IBinder onBind(Intent intent) {
//        return binder;
//    }

    /**
     * 这是学习Messenger的代码，可以看到基本无差别，只是将messenger作为IBinder返回给客户端
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
