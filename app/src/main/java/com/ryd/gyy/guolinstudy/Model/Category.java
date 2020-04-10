package com.ryd.gyy.guolinstudy.Model;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Category extends LitePalSupport {

    private int id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    Category和News多对多的关系
    private List<News> categoryList = new ArrayList<News>();

    // 自动生成get、set方法
}
