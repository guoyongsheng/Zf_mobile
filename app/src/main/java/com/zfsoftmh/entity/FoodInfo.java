package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ljq
 * on 2017/7/28.
 * 食物的详细信息
 */

public class FoodInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    String foodId;
    String foodName;
    String picPath;
    String storage;
    String isactive;
    String price;
    String type_title;//用于存储该食物的类别
    int count;



    public FoodInfo() {

        count = 0;
        foodId = "";
        foodName = "";
        picPath = "";
        storage = "";
        isactive = "";
        price = "";
        type_title = "";

    }


    protected FoodInfo(Parcel in) {
        foodId = in.readString();
        foodName = in.readString();
        picPath = in.readString();
        storage = in.readString();
        isactive = in.readString();
        price = in.readString();
        type_title = in.readString();
        count = in.readInt();
    }


    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getStorage() {
        return Integer.valueOf(storage);
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public boolean getIsactive() {
        if (isactive.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public double getPrice() {
        return Double.valueOf(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
