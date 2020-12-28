package com.ryd.gyy.guolinstudy.testjava;

public class Person2 {
    public static int value1 = 100;
    public static final int value2 = 200;

    public static Person2 p = new Person2();
    public int value4 = 400;

    static{
        value1 = 101;
        System.out.println("1");
    }

    {
        value1 = 102;
        System.out.println("2");
    }

    public Person2(){
        value1 = 103;
        System.out.println("3");
    }
}
