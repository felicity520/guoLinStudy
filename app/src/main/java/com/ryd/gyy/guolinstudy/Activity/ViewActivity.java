package com.ryd.gyy.guolinstudy.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Thread.ThreadStudy;

import java.lang.ref.WeakReference;

public class ViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_virwgroup_test);
        setContentView(R.layout.activity_virwgroup_test1);


    }

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


}
