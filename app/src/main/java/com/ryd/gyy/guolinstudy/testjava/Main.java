package com.ryd.gyy.guolinstudy.testjava;

import java.lang.reflect.Method;

/**
 * 获取Student类的main方法、不要与当前的main方法搞混了
 */
public class Main {

    public static void main(String[] args) {
        try {
            //1、获取Student对象的字节码
            Class clazz = Class.forName("com.ryd.gyy.guolinstudy.testjava.Student");

            //2、获取main方法
            Method methodMain = clazz.getMethod("main", String[].class);//第一个参数：方法名称，第二个参数：方法形参的类型，
            //3、调用main方法
            // methodMain.invoke(null, new String[]{"a","b","c"});
            //第一个参数，对象类型，因为方法是static静态的，所以为null可以，第二个参数是String数组，这里要注意在jdk1.4时是数组，jdk1.5之后是可变参数
            //这里拆的时候将  new String[]{"a","b","c"} 拆成3个对象。。。所以需要将它强转。
            methodMain.invoke(null, (Object) new String[]{"a", "b", "c"});//方式一
            // methodMain.invoke(null, new Object[]{new String[]{"a","b","c"}});//方式二

        } catch (Exception e) {
            e.printStackTrace();
        }


//        new Thread1().start();
//        new Thread2().start();
        new Thread3().start();
        new Thread4().start();

    }

    // volatile学习参考郭神的文章：https://mp.weixin.qq.com/s/WtMeB-4sXOYQtvYKUx6c5Q
    //主要用在多线程编程
    static volatile boolean flag;

    static class Thread1 extends Thread {
        @Override
        public void run() {
            while (true) {
                if (flag) {
                    flag = false;
                    System.out.println("Thread1 set flag to false");
                }
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            while (true) {
                if (!flag) {
                    flag = true;
                    System.out.println("Thread2 set flag to true");
                }
            }
        }
    }


    static volatile boolean init;
    static String value;

    static class Thread3 extends Thread {
        @Override
        public void run() {
            value = "hello world";
            init = true;
        }
    }

    static class Thread4 extends Thread {
        @Override
        public void run() {
            while (!init) {
                // 等待初始化完成
            }
            value.toUpperCase();
        }
    }

    /**
     * volatile在android中的应用
     * <p>
     * 存在着这样一种可能，就是我们明明已经将isCanceled变量设置成了true，
     * 但是download()方法所使用的CPU高速缓存中记录的isCanceled变量还是false，
     * 从而导致下载无法被取消的情况出现。
     * //     *
     * 因此，最安全的写法就是对isCanceled变量声明volatile关键字：
     */
    public class DownloadTask {

        volatile boolean isCanceled = false;

        public void download() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isCanceled) {
//                        byte[] bytes = readBytesFromNetwork();
//                        if (bytes.length == 0) {
//                            break;
//                        }
//                        writeBytesToDisk(bytes);
                    }
                }
            }).start();
        }

        public void cancel() {
            isCanceled = true;
        }

    }


}
