package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-6-22
 * @Description: 招领类型的实体类
 */

public class DisCoveryTypeInfo {

    private int type; //1: 有学校招领处  2: 没有学校招领处

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
