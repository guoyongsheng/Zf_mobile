package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/26
 * @Description: 实体类
 */

public class AppServiceInfo {

    private String userId;
    private List<String> serviceList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }
}
