package com.ryd.gyy.guolinstudy.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ryd.gyy.guolinstudy.Fragment.Fragment2;
import com.ryd.gyy.guolinstudy.Fragment.Fragment4;
import com.ryd.gyy.guolinstudy.R;

/**
 * 用于通知下拉的Activity
 * 学习Fragment，必须继承FragmentActivity
 */
public class NotificationActivity extends FragmentActivity {

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;

    Fragment2 fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification2);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局

        setContentView(R.layout.activity_fragment_test);

        addFragByDynamic();
        initView();

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
