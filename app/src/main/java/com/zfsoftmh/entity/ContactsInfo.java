package com.zfsoftmh.entity;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 通讯录对象
 */

public class ContactsInfo implements Parent<ContactsItemInfo> {

    private int type; // 0: 不能展开 1: 可以展开
    private String name; //名称
    private int imageId; //图片的资源id
    private List<ContactsItemInfo> list = new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setList(List<ContactsItemInfo> list) {
        this.list = list;
    }

    @Override
    public List<ContactsItemInfo> getChildList() {
        return list;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
