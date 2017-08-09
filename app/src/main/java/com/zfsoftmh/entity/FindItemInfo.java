package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: 实体类
 */

public class FindItemInfo {

    private int resId; //资源id
    private String name; //名称

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
