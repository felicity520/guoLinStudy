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
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Service.TestService1;
import com.ryd.gyy.guolinstudy.Service.TestService2;
import com.ryd.gyy.guolinstudy.Service.TestService3;

import java.lang.ref.WeakReference;


//import com.ryd.gyy.guolinstudy.R;

//import java.lang.ref.WeakReference;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = "TestService2";

    /*
    错误的写法
    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        }
    }
    */

    /**
     * 正确的写法：防止内存泄漏
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


    private MyFrameCallback mFrameCallback = new MyFrameCallback();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Choreographer.getInstance().postFrameCallback(mFrameCallback);

        setContentView(R.layout.activity_test_service);

        initService();
        initBindService();
        initIntentService();
        initFrame();

//        setContentView(R.layout.activity_virwgroup_test);
//        setContentView(R.layout.activity_virwgroup_test1);

    }

    private void initFrame() {
        Button btnframe = findViewById(R.id.btnframe);
        btnframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiLongTimeWork();
            }
        });
    }


    public class MyFrameCallback implements Choreographer.FrameCallback {
        private long lastTime = 0;

        @Override
        public void doFrame(long frameTimeNanos) {

            long times = (frameTimeNanos - lastTime) / 1000000;
            int frames = (int) (times / 16);

            String TAG = "MyFrameCallback";
            if (times > 16) {
                Log.w(TAG, "UI线程超时(超过16ms):" + times + "ms" + " , 丢帧:" + frames);
            } else {
                Log.w(TAG, "UI线程刷新正常");
            }

            lastTime = frameTimeNanos;
            //每一次订阅都只会接收一次 vsync 信号，而我们需要一直监听 doFrame 的回调，因此在方法最后需要递归的执行 postFrameCallback 方法。
            Choreographer.getInstance().postFrameCallback(mFrameCallback);
        }
    }

    private void uiLongTimeWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
//        注意及时移除，要不然会内存泄漏
        Choreographer.getInstance().removeFrameCallback(mFrameCallback);
        super.onDestroy();
    }

    private void initIntentService() {
        Intent it1 = new Intent(this, TestService3.class);
        Bundle b1 = new Bundle();
        b1.putString("param", "s1");
        it1.putExtras(b1);

        Intent it2 = new Intent(this, TestService3.class);
        Bundle b2 = new Bundle();
        b2.putString("param", "s2");
        it2.putExtras(b2);

        Intent it3 = new Intent(this, TestService3.class);
        Bundle b3 = new Bundle();
        b3.putString("param", "s3");
        it3.putExtras(b3);

        //接着启动多次IntentService,每次启动,都会新建一个工作线程
        //但始终只有一个IntentService实例
        startService(it1);
        startService(it2);
        startService(it3);
    }

    private void initBindService() {
        Button btnbind = (Button) findViewById(R.id.btnbind);
        Button btnrebind = (Button) findViewById(R.id.btnrebind);
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

        Button btnbindstart = (Button) findViewById(R.id.btnbindstart);
        Intent startIntent = new Intent(this, TestService2.class);
        btnbindstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bind之后再start
                startService(startIntent);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除service绑定
                unbindService(conn);
            }
        });

        final Intent intentRe = new Intent(this, TestService1.class);
        btnrebind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新绑定service
                bindService(intentRe, connRe, Service.BIND_AUTO_CREATE);
            }
        });
        Button btncancelrebind = (Button) findViewById(R.id.btncancelrebind);
        btncancelrebind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除service绑定
                unbindService(connRe);
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
            Log.d(TAG, "------Service DisConnected------- ");
        }

        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "------Service Connected-------");
            binder = (TestService2.MyBinder) service;
        }
    };


    TestService1.MyBinder binderRe;
    private ServiceConnection connRe = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            异常断开时回调
            Log.d(TAG, "------TestService1 Service DisConnected------- ");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "------TestService1 Service Connected-------");
            binderRe = (TestService1.MyBinder) service;
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
