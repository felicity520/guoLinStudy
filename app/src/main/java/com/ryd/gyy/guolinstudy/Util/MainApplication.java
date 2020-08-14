package com.ryd.gyy.guolinstudy.Util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.ryd.gyy.guolinstudy.Thread.CrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = getApplicationContext();
        SDKInitializer.initialize(this);//百度要求添加的
        CrashHandler.getInstance().init(this);

//        Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "34141d8fcf", true);


    }

    public static Context getGlobalContext() {
        return mContext;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
