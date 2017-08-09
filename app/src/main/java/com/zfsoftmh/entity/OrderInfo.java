package com.zfsoftmh.entity;

import java.util.List;

/**
 * Created by li
 * on 2017/8/3.
 */

public class OrderInfo {
    String userid;
    String personnumber;
    String arrivetime;
    String description;
    String addressid;
    String canteenid;
    String summation;
    List<OrderFoodInfo> list;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPersonnumber() {
        return personnumber;
    }

    public void setPersonnumber(String personnumber) {
        this.personnumber = personnumber;
    }

    public String getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getCanteenid() {
        return canteenid;
    }

    public void setCanteenid(String canteenid) {
        this.canteenid = canteenid;
    }

    public String getSummation() {
        return summation;
    }

    public void setSummation(String summation) {
        this.summation = summation;
    }

    public List<OrderFoodInfo> getList() {
        return list;
    }

    public void setList(List<OrderFoodInfo> list) {
        this.list = list;
    }
}
