package com.ryd.gyy.guolinstudy.Util;

public class Singleton {
    private static class SingletonHoler {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Singleton instance = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHoler.instance;
    }

}
