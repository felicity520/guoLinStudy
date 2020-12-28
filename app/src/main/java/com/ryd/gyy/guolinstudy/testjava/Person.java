package com.ryd.gyy.guolinstudy.testjava;

public class Person {
    public static int value1 = 100;
    public static final int value2 = 200;

    public int value4 = 400;

    static {
        value1 = 101;
        System.out.println("1");
    }

    public Person() {
        value1 = 103;
        System.out.println("4");
    }

    {
        value1 = 102;
        System.out.println("3");
    }


}

