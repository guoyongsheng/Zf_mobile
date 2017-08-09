package com.zfsoftmh.entity;

/**
 * Created by ljq on 2017/7/26.
 * 订餐页面的左边recyclerview的标题
 */

public class EateryTitleInfo {

    String titlename;//标题名
    int  pos;//标题所在位置

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
