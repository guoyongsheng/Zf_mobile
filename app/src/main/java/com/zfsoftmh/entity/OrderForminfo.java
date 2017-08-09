package com.zfsoftmh.entity;

import com.zfsoftmh.common.utils.GsonHelper;

import java.util.List;

/**
 * Created by li
 * on 2017/7/24.
 */

public class OrderForminfo {
    String orderId;
    String userId;
    String flag;
    String canteenId;
    String createtimeStr;
    String summation;
    EateryInfo canteen;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getCreatetimeStr() {
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public String getSummation() {
        return summation;
    }



    public void setSummation(String summation) {
        this.summation = summation;
    }

    public EateryInfo getCanteen() {
        return canteen;
    }

    public void setCanteen(EateryInfo canteen) {
        this.canteen = canteen;
    }
}
