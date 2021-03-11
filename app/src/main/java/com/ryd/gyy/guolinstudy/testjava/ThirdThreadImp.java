package com.ryd.gyy.guolinstudy.testjava;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThirdThreadImp {

    public static void main(String[] args) {

        //这里call()方法的重写是采用lambda表达式，没有新建一个Callable接口的实现类
        FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>) () -> {
            int i = 0;
            for (; i < 50; i++) {
                System.out.println(Thread.currentThread().getName() +
                        "  的线程执行体内的循环变量i的值为：" + i);
            }
            //call()方法的返回值
            return i;
        });

        for (int j = 0; j < 50; j++) {
            System.out.println(Thread.currentThread().getName() + " 大循环的循环变量j的值为：" + j);
            if (j == 20) {
                new Thread(task, "有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程的返回值：" + task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
