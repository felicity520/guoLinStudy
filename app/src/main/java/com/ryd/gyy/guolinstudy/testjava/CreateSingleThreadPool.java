package com.ryd.gyy.guolinstudy.testjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按先进先出的顺序执行。
 */
public class CreateSingleThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;

            singleThreadExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    //线程：pool-1-thread-1 正在执行 task: 1
                    //线程：pool-1-thread-1 正在执行 task: 2
                    //线程：pool-1-thread-1 正在执行 task: 3
                    //线程：pool-1-thread-1 正在执行 task: 4
                    //线程：pool-1-thread-1 正在执行 task: 5
                    //可以看到只有一个线程
                    System.out.println("线程：" + Thread.currentThread().getName()
                            + " 正在执行 task: " + taskId);
                }

            });
            Thread.sleep(1000);
        }
    }
}
