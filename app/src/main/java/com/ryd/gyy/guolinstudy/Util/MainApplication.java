package com.ryd.gyy.guolinstudy.Util;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import org.litepal.LitePal;

public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = getApplicationContext();
        SDKInitializer.initialize(this);//百度要求添加的
    }

    public static Context getGlobalContext() {
        return mContext;
    }
}
