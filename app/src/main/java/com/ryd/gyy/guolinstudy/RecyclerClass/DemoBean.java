package com.ryd.gyy.guolinstudy.RecyclerClass;

public class DemoBean {

    private String name;

    private Class action;

    private int imageId;

    public DemoBean(String name, int imageId, Class action) {
        this.name = name;
        this.imageId = imageId;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public Class getAction() {
        return action;
    }
}
