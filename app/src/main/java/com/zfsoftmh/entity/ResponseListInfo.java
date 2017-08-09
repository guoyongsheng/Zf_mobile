package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-8
 * @Description: 所有的滚动加载数据格式都是这样的
 */

public class ResponseListInfo<T> {

    private boolean isOvered; //是否已经结束了
    private List<T> itemList; //业务数据

    public boolean isOvered() {
        return isOvered;
    }

    public void setOvered(boolean overed) {
        isOvered = overed;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public List<T> getItemList() {
        return itemList;
    }
}
