package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ryd.gyy.guolinstudy.R;

public class SendbrocastActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sendbrocast;
    private Button btn_sendlocalbrocast;

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendbrocast);
        initView();
        initData();
    }

    private void initData() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.gyy.broadcast.customize");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter); // 注册本地广播监听器
    }

    private void initView() {
//        发送自定义广播
        btn_sendbrocast = (Button) findViewById(R.id.btn_sendbrocast);
        btn_sendbrocast.setOnClickListener(this);
//        发送自定义广播
        btn_sendlocalbrocast = (Button) findViewById(R.id.btn_sendlocalbrocast);
        btn_sendlocalbrocast.setOnClickListener(this);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendbrocast:
//                发送自定义广播
                Intent intent = new Intent("com.gyy.broadcast.customize");
                intent.addFlags(0x01000000);//自定义广播也需要加上这句话
                sendOrderedBroadcast(intent, null);//发送有序广播。第二个参数是权限相关的字符串，传入null即可
//                sendBroadcast(intent);
                break;
            case R.id.btn_sendlocalbrocast:
//                发送本地广播
//                注意：本地广播只能动态注册。因为静态注册主要是为了在程序未启动的时候接收广播，而我们在发送本地广播的时候明显程序已经启动了，所以就不需要静态注册了。
                Intent intentLocal = new Intent("com.gyy.broadcast.customize");
                intentLocal.addFlags(0x01000000);//自定义广播也需要加上这句话
                localBroadcastManager.sendBroadcast(intentLocal); // 发送本地广播
                break;
            default:
                break;
        }
    }
}
