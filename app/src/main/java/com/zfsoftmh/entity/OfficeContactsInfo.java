package com.zfsoftmh.entity;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/11
 * @Description: 办公通讯录的实体类
 */

public class OfficeContactsInfo implements Parent<ContactsItemInfo> {

    private String name;
    private List<ContactsItemInfo> list = new ArrayList<>();


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
