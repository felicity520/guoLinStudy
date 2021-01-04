package com.ryd.gyy.guolinstudy.testjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * 解释：已经处于空闲状态的线程，可以被复用执行任务。
 */
public class CreateCacheThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Thread.sleep(1500);

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程：" + Thread.currentThread().getName()
                                + " 正在执行 task: " + taskId);
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                }
            });
        }
        cachedThreadPool.shutdown();
    }
}

