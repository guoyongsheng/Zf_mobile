package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017-7-5
 * @Description: 兴趣圈的实体类
 */

public class InterestCircleItemInfo implements Parcelable {

    private String url; //图片的url
    private String name; //名称
    private int number; //数量
    private String description; //简介

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeInt(this.number);
        dest.writeString(this.description);
    }

    public InterestCircleItemInfo() {
    }

    private InterestCircleItemInfo(Parcel in) {
        this.url = in.readString();
        this.name = in.readString();
        this.number = in.readInt();
        this.description = in.readString();
    }

    public static final Creator<InterestCircleItemInfo> CREATOR = new Creator<InterestCircleItemInfo>() {
        @Override
        public InterestCircleItemInfo createFromParcel(Parcel source) {
            return new InterestCircleItemInfo(source);
        }

        @Override
        public InterestCircleItemInfo[] newArray(int size) {
            return new InterestCircleItemInfo[size];
        }
    };
}
