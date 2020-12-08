package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ryd.gyy.guolinstudy.R;

public class ProcessActivity2 extends AppCompatActivity {

    private static final String TAG = "ProcessActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process2);

        Log.e(TAG, "ProcessActivity2 onCreate TEST_ID: " + MyActivity.TEST_ID);
    }
}