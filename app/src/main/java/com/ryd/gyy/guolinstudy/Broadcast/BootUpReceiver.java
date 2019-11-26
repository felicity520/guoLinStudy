package com.ryd.gyy.guolinstudy.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ryd.gyy.guolinstudy.Activity.DownLoadActivity;

public class BootUpReceiver extends BroadcastReceiver {

    private static final String TAG = "BootUpReceiver";
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals(ACTION)) {
            Log.i(TAG, "接收到开机广播了: ");
            Intent i = new Intent(context, DownLoadActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
