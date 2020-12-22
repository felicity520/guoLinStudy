package com.ryd.gyy.guolinstudy.testjava;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestClassLoader {
    public static void main(String[] args) {
        DiskClassLoader diskClassLoader = new DiskClassLoader("file:///D/Android/Projects/demo/guoLinStudy/app/src/main/java/com/ryd/gyy/guolinstudy/testjava/");
        Class aClass;
        {
            try {
                aClass = diskClassLoader.loadClass("Secret");
                if (aClass != null) {
                    Object object = aClass.newInstance();
                    Method method = aClass.getDeclaredMethod("printSecret", null);
                    method.invoke(object, null);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}

