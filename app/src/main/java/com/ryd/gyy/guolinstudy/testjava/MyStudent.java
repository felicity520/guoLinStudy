package com.ryd.gyy.guolinstudy.testjava;

public class MyStudent extends Person {
    public static int value3 = 300;

    public int value5 = 500;

    static {
        value3 = 301;
        System.out.println("2");
    }

    public MyStudent() {
        value3 = 303;
        System.out.println("6");
    }

    {
        value3 = 302;
        System.out.println("5");
    }


}
