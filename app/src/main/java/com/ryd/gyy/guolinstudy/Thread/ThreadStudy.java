package com.ryd.gyy.guolinstudy.Thread;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.Model.JianzhiResult;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Util.JsoupTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 参考：https://www.jianshu.com/p/49349eee9abc
 */
public class ThreadStudy extends AppCompatActivity implements View.OnClickListener {

    //pn是第几页，rn是一页显示多少条数据：https://zhaopin.baidu.com/jianzhi?city=温州&pn=5&rn=3
    private String POST_URL = "https://zhaopin.baidu.com/jianzhi?city=温州&pn=2&rn=3";

    private JsoupTool jsoupTool;

    Thread secondThread;//第二类方法的线程

    private static final String TAG = "ThreadStudy";

    public volatile boolean stop = false;

    MyThread mMyThread;
    private Button threadStart;
    private Button threadStop;
    private Button btn_sendRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        initView();
        initData();
        Log.i(TAG, "onCreate----------: " + getMainLooper().getThread().getId());//获取主线程id
//        thirdKinds();
    }

    private void initData() {
        jsoupTool = new JsoupTool();
    }

    private void initView() {
        threadStart = (Button) findViewById(R.id.threadStart);
        threadStart.setOnClickListener(this);

        threadStop = (Button) findViewById(R.id.threadStop);
        threadStop.setOnClickListener(this);

        btn_sendRequest = (Button) findViewById(R.id.btn_sendRequest);
        btn_sendRequest.setOnClickListener(this);

        btn_sendRequest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch: " + event.getAction());
                return false;
                //onTouch() 执行总优先于 onClick()
                //onTouch() 方法优先级高于 onTouchEvent(event) 方法
                //return true;//返回true就认为这个事件被onTouch消费掉了，因而不会再继续向下传递。
            }
        });
    }


    private void firstKind() {
        mMyThread = new MyThread();
        mMyThread.start();
    }

    private void secondKind() {
        MyRunnable MyRunnable = new MyRunnable();
        secondThread = new Thread(MyRunnable);
        secondThread.start();
    }

    /**
     * 第三种方式：不用专门像第二种方法实现Runnable接口，这里直接使用匿名内部类的方式
     */
    private void thirdKinds() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //使用标志位来终止线程
//                int i = 0;
//                while (!stop) {
//                    // thread runing
//                    Log.v(TAG, "gyy run: " + i++);
//                    stop = true;
//                }


            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.threadStart:
//                firstKind();
                secondKind();
                break;
            case R.id.threadStop:
//                stopThread();
                stopSecondThread();
                break;
            case R.id.btn_sendRequest:
                //跳转到某个app的代码
                String packname = "example.xmryd.com.a8841factory";
                PackageManager packageManager = getPackageManager();

                if (checkPackInfo(packname)) {
                    Intent intent = packageManager.getLaunchIntentForPackage(packname);
                    startActivity(intent);
                } else {
                    //没有安装app要做的事情
                    Toast.makeText(ThreadStudy.this, "没有安装example.xmryd.com.a8841factory" + packname, LENGTH_SHORT).show();
                }


                jsoupTool.runJsoup(POST_URL, new JsoupTool.JsoupResultListener() {
                    @Override
                    public void onSuccess(ArrayList<JianzhiResult> results) {
                        Log.i("TAG", results.toString());
                    }

                    @Override
                    public void onFail(ArrayList<JianzhiResult> results) {

                    }
                });
                break;
        }
    }


    /**
     * 检查包是否存在(存在：true  不存在：false)
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    /**
     * interrupt()中断线程
     */
    private void stopThread() {
        Log.e(TAG, "mMyThread: " + mMyThread);
        Log.e(TAG, "mMyThread.isAlive(): " + mMyThread.isAlive());
        if (null != mMyThread && mMyThread.isAlive()) {
            Log.e(TAG, "stopThread: -----");
            mMyThread.interrupt();
            mMyThread = null;
        }
    }

    /**
     * interrupt()中断线程
     */
    private void stopSecondThread() {
        Log.e(TAG, "secondThread: " + secondThread);
        Log.e(TAG, "secondThread.isAlive(): " + secondThread.isAlive());
        if (null != secondThread && secondThread.isAlive()) {
            Log.e(TAG, "stopThread  secondThread: -----");
            secondThread.interrupt();
            secondThread = null;
        }
    }


    /**
     * 第一种：继承线程
     */
    public class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            //针对线程正常运行状态
//            int i = 0;
//            // 判断状态，如果被打断则跳出并将线程置空
//            while (!isInterrupted()) {
//                i++;
//                Log.i(TAG, "线程名字" + Thread.currentThread().getName() + "  Count i:" + i);
//            }


            //针对线程阻塞状态
//            int i = 0;
//            while(true){
//                try {
//                    i++;
//                    Thread.sleep(1000);
//                    Log.i(TAG,Thread.currentThread().getName()+":Running()_Count:"+i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.i(TAG,Thread.currentThread().getName()+"异常抛出，停止线程");
//                    break;
//                }
//            }

            //终极方法
            int i = 0;
            while (!isInterrupted()) {  // 判断线程是否被打断
                try {
                    i++;
                    Thread.sleep(1000);
                    Log.i("thread", Thread.currentThread().getName() + ":Running()_Count:" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("thread", Thread.currentThread().getName() + "异常抛出，停止线程");
                    break;// 抛出异常跳出循环
                }
            }
        }
    }


    /**
     * 第二种：实现Runnable,确认是在子线程Thread.currentThread().getId()
     */
    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            // do something
            Log.i(TAG, "onCreate4: " + Thread.currentThread().getId());//当前线程ID
            Log.i(TAG, "onCreate5: " + getMainLooper().getThread().getId());//主线程ID
            Log.e(TAG, "run: -------------");
        }
    }


    //保存点击的时间
    private long exitTime = 0;

    //只在当前的activity
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                Log.i(TAG, "退出时间 : " + exitTime);
                stampToDate(exitTime);
                Log.i(TAG, "转换后的时间 : " + stampToDate(exitTime));
            } else {
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* * 将时间戳转换为时间 */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

}
