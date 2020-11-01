package com.ryd.gyy.guolinstudy.testjava;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.annotation.*;

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

//测试volatile
//        new Thread1().start();
//        new Thread2().start();
//        new Thread3().start();
//        new Thread4().start();

        AnnoationTest annotation = new AnnoationTest();
        annotation.setName("abcefg");
        annotation.setPassword("12345678901111");
        try {
            valid(annotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Java通过反射机制获取类、方法、属性上的注解，
     * 因此java.lang.reflect提供AnnotationElement支持注解，主要方法如下：
     * (1)
     * 格式：<T extends Annotation> T getAnnotation(Class<T> annotationClass)
     * 说明：获取该元素上annotationClass类型的注解，如果没有返回null
     *
     * @param obj
     * @throws IllegalAccessException
     */
    public static void valid(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        System.out.println("clazz-----" + clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("field-----" + field.getName());
            Test test = field.getAnnotation(Test.class);//获取属性上的@Test注解
            if (test != null) {
                field.setAccessible(true);//设置属性可访问
                System.out.println("field.getGenericType()-----" + field.getGenericType().toString());
                if ("class java.lang.String".equals(field.getGenericType().toString())) {//字符串类型的才判断长度
                    //field.get(类)：获取属性的值
                    String value = (String) field.get(obj);
                    if (value != null && ((value.length() > test.max()) || value.length() < test.min())) {
                        System.out.println(test.description());
                    }
                }
            }
        }
    }


    /**
     * 定义一个注解
     */
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test {

        /**
         * max()也叫注解中的参数类型,min()和description()同理
         *
         * @return
         */
        int max() default 0;

        int min() default 0;

        String description() default "";
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
