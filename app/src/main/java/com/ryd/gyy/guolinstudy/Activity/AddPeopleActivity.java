package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.ryd.gyy.guolinstudy.R;

import javax.security.auth.login.LoginException;

/**
 * 透明的dialog
 */
public class AddPeopleActivity extends AppCompatActivity {

    private static final String TAG = "AddPeopleActivity_B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        setContentView(R.layout.activity_add_people);

        Log.e(TAG, TAG + "  onCreate: ------");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, TAG + " onPause: -----");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, TAG + " onStart: -----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, TAG + " onResume: ---------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, TAG + " onStop: -----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, TAG + " onDestroy: ---------");
    }
}