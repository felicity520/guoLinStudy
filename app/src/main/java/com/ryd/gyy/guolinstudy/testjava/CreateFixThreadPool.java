package com.ryd.gyy.guolinstudy.testjava;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个固定数目的、可重用的线程池。
 */
public class CreateFixThreadPool {

    public static void main(String[] args) {
        // 创建线程数量为3的线程池
        ExecutorService singleThreadExecutor = Executors.newFixedThreadPool(3);
        // 提交10个任务交给线程池执行
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;

            singleThreadExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    Date date = new Date();
                    System.out.println("线程：" + Thread.currentThread().getName() + "时间：" + date
                            + " 正在执行 task: " + taskId);
                }

            });
            // 延迟2s向线程池中提交任务
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

