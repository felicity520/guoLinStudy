package com.ryd.gyy.guolinstudy.testjava;

public class StrongReference {

    public static void main(String[] args) {
        StrongReference strongReference = new StrongReference();
        strongReference.method1();
    }


    public void method1() {
        Object object = new Object();
        Object[] objects = new Object[Integer.MAX_VALUE];
    }

}
