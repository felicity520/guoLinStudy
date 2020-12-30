package com.ryd.gyy.guolinstudy.testjava;

public class LagouSynchronizedMehtods {
    private final Object lock = new Object();

    public static void main(String[] args) {
        LagouSynchronizedMehtods l1 = new LagouSynchronizedMehtods();
        LagouSynchronizedMehtods l2 = new LagouSynchronizedMehtods();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                l1.printLog();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                不同实例对象之间不会有互斥效果。也就是线程1和线程2交替运行
                l2.printLog();
//                只有同一个实例对象调用此方法才会产生互斥效果，也就是线程1运行完才会执行线程2
//                l1.printLog();
            }
        });

        t1.start();
        t2.start();
    }


    /**
     * 修饰代码块
     */
    public void printLog() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        }
    }

    /**
     * 修饰实例方法
     */
//    public synchronized void printLog() {
//        for (int i = 0; i < 5; i++) {
//            System.out.println(Thread.currentThread().getName() + " is printing " + i);
//        }
//    }


    /**
     * 修饰静态方法
     */
//    public synchronized static void printLog() {
//        for (int i = 0; i < 5; i++) {
//            System.out.println(Thread.currentThread().getName() + " is printing " + i);
//        }
//    }
}

