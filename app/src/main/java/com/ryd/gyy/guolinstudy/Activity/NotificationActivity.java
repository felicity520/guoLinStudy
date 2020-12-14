package com.ryd.gyy.guolinstudy.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ryd.gyy.guolinstudy.Fragment.Fragment2;
import com.ryd.gyy.guolinstudy.Fragment.Fragment4;
import com.ryd.gyy.guolinstudy.Model.Book;
import com.ryd.gyy.guolinstudy.Model.Person;
import com.ryd.gyy.guolinstudy.R;

/**
 * 用于通知下拉的Activity
 * 学习Fragment，必须继承FragmentActivity
 */
public class NotificationActivity extends FragmentActivity {

    private static final String TAG = "NotificationActivity";

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;

    Fragment2 fragment;

    private Button mBtnBasic = null;
    private Button mBtnPar = null;
    private Button mBtnSer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification2);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局

        setContentView(R.layout.activity_fragment_test);

        addFragByDynamic();
        initView();
        testProcess();

        Log.i(TAG, "onCreate currentThread: " + Thread.currentThread().getId());

    }

    /**
     * 测试多进程的代码
     */
    private void testProcess() {
        Button btn_start_process = findViewById(R.id.btn_start_process);
        btn_start_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NotificationActivity.this, ProcessActivity.class);
//                startActivity(intent);

                new Thread() {
                    public void run() {
                        //在子线程启动后的service线程还是在主线程
                        //就跟在子线程里面启动activity一样，新启动的activity肯定是在主线程，
                        // 跟启动流程有关，可以去研究下，好像是handler切换了下
                        Log.i(TAG, "btn_start_process currentThread: " + Thread.currentThread().getId());
                        Intent intent = new Intent(NotificationActivity.this, MaterialActivity.class);
                        startActivity(intent);
                    }
                }.start();
            }
        });
        Button btn_start_process2 = findViewById(R.id.btn_start_process2);
        btn_start_process2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "skywang");
                bundle.putInt("height", 175);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //学习序列化
        mBtnBasic = (Button) findViewById(R.id.btnBasic);
        mBtnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBasicDataThroughBundle();
            }
        });

        mBtnPar = (Button) findViewById(R.id.btnPar);
        mBtnPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendParcelableDataThroughBundle();
            }
        });

        mBtnSer = (Button) findViewById(R.id.btnSer);
        mBtnSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSeriableDataThroughBundle();
            }
        });
    }

    // sent object through Pacelable
    private void sendParcelableDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Book mBook = new Book();
        mBook.setBookName("Android");
        mBook.setAuthor("skywang");
        mBook.setPublishTime(2013);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable("ParcelableValue", mBook);
        intent.putExtras(mBundle);

        startActivity(intent);
    }

    // sent object through seriable
    private void sendSeriableDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Person mPerson = new Person();
        mPerson.setName("skywang");
        mPerson.setAge(24);

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("SeriableValue", mPerson);
        intent.putExtras(mBundle);

        startActivity(intent);
    }


    private void sendBasicDataThroughBundle() {
        Intent intent = new Intent(NotificationActivity.this, ProcessActivity2.class);

        Bundle bundle = new Bundle();
        bundle.putString("name", "skywang");
        bundle.putInt("height", 175);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    private void initView() {
        Button btn_switch = findViewById(R.id.btn_switch);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment4 fragment4 = new Fragment4();
                fragmentTransaction.replace(R.id.fragment_container, fragment4).commit();
            }
        });
    }

    /**
     * 动态添加Fragment
     */
    private void addFragByDynamic() {
        // 步骤1：获取FragmentManager，androidx使用getSupportFragmentManager
        fragmentManager = getSupportFragmentManager();

        // 步骤2：获取FragmentTransaction
        fragmentTransaction = fragmentManager.beginTransaction();

        // 步骤3：创建需要添加的Fragment ：Fragment2
        fragment = new Fragment2();

        // 步骤4：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        fragmentTransaction.add(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
    }
}
