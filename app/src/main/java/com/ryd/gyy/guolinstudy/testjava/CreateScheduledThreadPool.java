package com.ryd.gyy.guolinstudy.testjava;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个定时线程池，支持定时及周期性任务执行。
 */
public class CreateScheduledThreadPool {
    private static int count = 0;

    public static void main(String[] args) throws Exception {

        // 指定线程数量为2的定时任务线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        // 延迟1s秒执行，每隔1s执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                System.out.println("线程 " + Thread.currentThread().getName() + "报时：" + date + "修改 count：" + count++);
            }

        }, 500, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(5000);
        // 使用shutdown关闭定时任务
        scheduledThreadPool.shutdown();
    }
}

