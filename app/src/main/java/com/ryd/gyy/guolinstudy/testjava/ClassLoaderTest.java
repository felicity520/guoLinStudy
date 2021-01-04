package com.ryd.gyy.guolinstudy.testjava;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {
    public static void main(String[] args) {
        DiskClassLoader diskClassLoader = new DiskClassLoader("C:\\");//1
        try {
            //关于调用loadClass和findClass的先后顺序
            //我认为，先执行loadClass，传入name，再通过loadClass的双亲委派制度最后执行到了findClass
            //所以是先调用load再调用find
            System.out.println("loadClass--------loadClass:");
            Class c = diskClassLoader.loadClass("com.ryd.gyy.guolinstudy.testjava.Jobs");//2
            //loadClass--------parent:sun.misc.Launcher$AppClassLoader@18b4aac2
            //loadClass--------parent1:sun.misc.Launcher$ExtClassLoader@36baf30c
            ClassLoader parent = c.getClassLoader().getParent();
            System.out.println("loadClass--------parent:" + parent);
            ClassLoader parent1 = parent.getParent();
            System.out.println("loadClass--------parent1:" + parent1);

            if (c != null) {
                try {
                    Object obj = c.newInstance();
                    System.out.println("最终加载的loader:" + obj.getClass().getClassLoader());
                    Method method = c.getDeclaredMethod("say", null);
                    method.invoke(obj, null);//3
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException
                        | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
