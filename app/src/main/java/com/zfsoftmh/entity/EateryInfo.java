package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ljq on 2017/7/21.
 * 食堂的信息
 */

public class EateryInfo implements Parcelable {

    String canteenId;
    String createtimeStr;
    String canteenName;
    String picPath;
    String lunchBox;
    String description;
    String fullDiscount;
    String isactive;


    public EateryInfo() {
        canteenId = "";
        createtimeStr = "";
        canteenName = "";
        picPath = "";
        lunchBox = "";
        description = "";
        fullDiscount = "";
        isactive = "";

    }

    protected EateryInfo(Parcel in) {
        canteenId = in.readString();
        createtimeStr = in.readString();
        canteenName = in.readString();
        picPath = in.readString();
        lunchBox = in.readString();
        description = in.readString();
        fullDiscount = in.readString();
        isactive = in.readString();
    }

    public static final Creator<EateryInfo> CREATOR = new Creator<EateryInfo>() {
        @Override
        public EateryInfo createFromParcel(Parcel in) {
            return new EateryInfo(in);
        }

        @Override
        public EateryInfo[] newArray(int size) {
            return new EateryInfo[size];
        }
    };

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

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getLunchBox() {
        return lunchBox;
    }

    public void setLunchBox(String lunchBox) {
        this.lunchBox = lunchBox;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFullDiscount() {
        return Integer.valueOf(fullDiscount);
    }

    public void setFullDiscount(String fullDiscount) {
        this.fullDiscount = fullDiscount;
    }

    public int getIsactive() {
        return Integer.valueOf(isactive);
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(canteenId);
        dest.writeString(createtimeStr);
        dest.writeString(canteenName);
        dest.writeString(picPath);
        dest.writeString(lunchBox);
        dest.writeString(description);
        dest.writeString(fullDiscount);
        dest.writeString(isactive);
    }
}
