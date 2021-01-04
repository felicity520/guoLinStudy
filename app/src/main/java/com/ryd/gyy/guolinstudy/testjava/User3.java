package com.ryd.gyy.guolinstudy.testjava;

import androidx.annotation.NonNull;

public class User3 implements Cloneable {
    private int age;
    private Favorite favorite;

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    @Override
    public Object clone() {
        User3 user2 = null;
        try {
            user2 = (User3) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user2;
    }
}
