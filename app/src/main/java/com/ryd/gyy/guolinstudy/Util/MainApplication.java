package com.ryd.gyy.guolinstudy.Util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.ryd.gyy.guolinstudy.Thread.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication extends Application {

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
