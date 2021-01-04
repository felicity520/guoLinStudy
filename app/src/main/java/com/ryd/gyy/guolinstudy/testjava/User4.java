package com.ryd.gyy.guolinstudy.testjava;

import androidx.annotation.NonNull;

public class User4 implements Cloneable {
    private int age;
    private Favorite2 favorite;

    public Favorite2 getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite2 favorite) {
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
        User4 user4 = null;
        try {
            user4 = (User4) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        user4.favorite = (Favorite2) favorite.clone();
        return user4;
    }
}
