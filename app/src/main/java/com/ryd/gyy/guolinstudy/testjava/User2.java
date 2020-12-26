package com.ryd.gyy.guolinstudy.testjava;

import androidx.annotation.NonNull;

public class User2 implements Cloneable {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    @Override
    public Object clone() {
        User2 user2 = null;
        try {
            user2 = (User2) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user2;
    }
}