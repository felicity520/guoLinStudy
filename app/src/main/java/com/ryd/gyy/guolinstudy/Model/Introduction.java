package com.ryd.gyy.guolinstudy.Model;

import org.litepal.crud.LitePalSupport;

public class Introduction extends LitePalSupport {

    private int id;

    private String guide;

    private String digest;

    // 自动生成get、set方法

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
