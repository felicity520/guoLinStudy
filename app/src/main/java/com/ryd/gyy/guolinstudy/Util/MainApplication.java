package com.ryd.gyy.guolinstudy.Util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.mapapi.SDKInitializer;
import com.ryd.gyy.guolinstudy.Thread.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    public static Context mContext;

    public RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //注意：尽量使用 Context.getApplicationContext，不要直接将 Activity 传递给其他组件。就是不要直接传this,防止第三方引用静态context
        mContext = getApplicationContext();

        LitePal.initialize(mContext);

//        SDKInitializer.initialize(this);//百度要求添加的
        CrashHandler.getInstance().init(mContext);

//        Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "34141d8fcf", true);


//        ThirdLibrary.init(this);

        // LeakCanary初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

        //获取当前的进程名
        String ProcessName = getCurrentProcessNameByActivityManager(mContext);
        int pid = android.os.Process.myPid();
        Log.e(TAG, "onCreate ProcessName: " + ProcessName + "-----pid:" + pid);
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    public static String getCurrentProcessNameByActivityManager(@NonNull Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    //这种情况会报错：原因是内部类不能持有静态引用
//    public class ThirdLibrary{
//
//        private static Context staticcontext;
//
//        public static void init(Context context){
//            if (staticcontext == null)
//                staticcontext = context;
//        }
//
//    }

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

