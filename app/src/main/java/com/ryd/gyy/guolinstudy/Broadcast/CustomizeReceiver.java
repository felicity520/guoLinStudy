package com.ryd.gyy.guolinstudy.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 自定义广播
 */
public class CustomizeReceiver extends BroadcastReceiver {

    private static final String TAG = "CustomizeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e(TAG, "received in CustomizeReceiver: ");
        Toast.makeText(context, "received in CustomizeReceiver", Toast.LENGTH_SHORT).show();
        abortBroadcast();//截断广播
    }
}
