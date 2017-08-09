package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/7/24.
 */

public class UserAddressInfo implements Parcelable {
    String userid;
    String addressId;
    String name;
    String mobilePhone;
    String schoolName;
    String specificAddress;




    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressId);
        dest.writeString(this.userid);
        dest.writeString(this.name);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.schoolName);
        dest.writeString(this.specificAddress);
    }

    protected UserAddressInfo(Parcel in) {
        addressId = in.readString();
        userid = in.readString();
        name = in.readString();
        mobilePhone = in.readString();
        schoolName = in.readString();
        specificAddress = in.readString();
    }

    public static final Parcelable.Creator<UserAddressInfo> CREATOR=new Parcelable.Creator<UserAddressInfo>(){

        @Override
        public UserAddressInfo createFromParcel(Parcel source) {
            return new UserAddressInfo(source);
        }

        @Override
        public UserAddressInfo[] newArray(int size) {
            return new UserAddressInfo[size];
        }
    };


}
