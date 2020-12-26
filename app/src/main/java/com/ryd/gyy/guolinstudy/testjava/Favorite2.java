package com.ryd.gyy.guolinstudy.testjava;

public class Favorite2 implements Cloneable {
    private String sport;
    private String color;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Object clone() {
        Favorite2 addr = null;
        try {
            addr = (Favorite2) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return addr;
    }
}

