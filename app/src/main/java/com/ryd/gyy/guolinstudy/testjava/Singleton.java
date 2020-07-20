package com.ryd.gyy.guolinstudy.testjava;

/***
 * 单例模式的经典实现
 */
public class Singleton {

    //被volatile关键字修饰的变量，如果值发生了变更，其他线程立马可见，避免出现脏读的现象。
    private volatile static Singleton singleton;

    public static void method() {

    }

    private Singleton() {

    }

    //第一版：为了防止多个线程同时第一次访问，而造成的线程安全问题，我们要多加一个同步锁:synchronized
//    public static synchronized Singleton getSingleton() {
//        if (singleton == null) {
//            singleton = new Singleton();
//        }
//        return singleton;
//    }

    //第二版：singleton不为空的情况下，不使用synchronized，这样可以避免内存的消耗，同时使用volatile关键词
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }

}
