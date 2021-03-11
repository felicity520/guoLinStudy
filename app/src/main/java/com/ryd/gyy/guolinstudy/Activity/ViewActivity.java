package com.ryd.gyy.guolinstudy.Activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Service.TestService1;
import com.ryd.gyy.guolinstudy.Service.TestService2;

import java.lang.ref.WeakReference;


//import com.ryd.gyy.guolinstudy.R;

//import java.lang.ref.WeakReference;

public class ViewActivity extends AppCompatActivity {

//    private class MyHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//        }
//    }

    /**
     * 防止内存泄漏
     */
    private static class MyHandler extends Handler {

        private WeakReference<ViewActivity> mActivity;

        public MyHandler(ViewActivity activity) {
            mActivity = new WeakReference<ViewActivity>(activity);
        }

        @Override

        public void handleMessage(Message msg) {
            ViewActivity activity = mActivity.get();
        }
    }


    private Button start;
    private Button stop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);

        initService();
        initBindService();

//        setContentView(R.layout.activity_virwgroup_test);
//        setContentView(R.layout.activity_virwgroup_test1);


    }

    private void initBindService() {
        Button btnbind = (Button) findViewById(R.id.btnbind);
        Button btncancel = (Button) findViewById(R.id.btncancel);
        Button btnstatus = (Button) findViewById(R.id.btnstatus);
        final Intent intent = new Intent(this, TestService2.class);
        btnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定service
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除service绑定
                unbindService(conn);
            }
        });

        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Service的count的值为:"
                        + binder.getCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //保持所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
    TestService2.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {

        //Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("------Service DisConnected-------");
        }

        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("------Service Connected-------");
            binder = (TestService2.MyBinder) service;
        }
    };

    private void initService() {
        start = (Button) findViewById(R.id.btnstart);
        stop = (Button) findViewById(R.id.btnstop);
        final Intent intent = new Intent(this, TestService1.class);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);

            }
        });
    }


}
