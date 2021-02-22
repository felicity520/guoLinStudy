package com.ryd.gyy.guolinstudy.testjava;

public class UserJiMu {

    private final String name = "Bob";
    private final static String nameStatic = "Bob1111";

    private final Student student = new Student();

    public String getName() {
        return name;
    }

    public Student getStudent() {
        return student;
    }
}