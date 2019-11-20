package com.ryd.gyy.guolinstudy.Thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;


/**
 * 参考：https://www.jianshu.com/p/49349eee9abc
 */
public class ThreadStudy extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ThreadStudy";

    public volatile boolean stop = false;

    MyThread mMyThread;
    private Button threadStart;
    private Button threadStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        initView();

//        thirdKinds();
    }

    private void initView() {
        threadStart = (Button) findViewById(R.id.threadStart);
        threadStart.setOnClickListener(this);

        threadStop = (Button) findViewById(R.id.threadStop);
        threadStop.setOnClickListener(this);
    }


    private void firstKind() {
        mMyThread = new MyThread();
        mMyThread.start();
    }

    private void secondKind() {
        MyRunnable MyRunnable = new MyRunnable();
        new Thread(MyRunnable).start();
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
                stopThread();
                break;
        }
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
            Log.i(TAG, "onCreate4: " + Thread.currentThread().getId());
            Log.i(TAG, "onCreate5: " + getMainLooper().getThread().getId());
            Log.e(TAG, "run: -------------");
        }
    }


}
