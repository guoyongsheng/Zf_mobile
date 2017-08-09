package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 通讯录item的实体
 */

public class ContactsItemInfo implements Parcelable{

    private int headerId; //头部的id
    private String id;
    private String name; //名称
    private String phone; //手机号码
    private String email; //邮箱
    private String department; //所在部门
    private String note; //备注
    private String photoUri; //头像路径
    private String firstLetter; //首字母

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        return firstLetter;
    }


    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public int getHeaderId() {
        return headerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.department);
        dest.writeString(this.note);
        dest.writeString(this.photoUri);
        dest.writeString(this.firstLetter);
        dest.writeInt(this.headerId);
    }

    public ContactsItemInfo() {
    }

    protected ContactsItemInfo(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.department = in.readString();
        this.note = in.readString();
        this.photoUri = in.readString();
        this.firstLetter = in.readString();
        this.headerId = in.readInt();
    }

    public static final Creator<ContactsItemInfo> CREATOR = new Creator<ContactsItemInfo>() {
        @Override
        public ContactsItemInfo createFromParcel(Parcel source) {
            return new ContactsItemInfo(source);
        }

        @Override
        public ContactsItemInfo[] newArray(int size) {
            return new ContactsItemInfo[size];
        }
    };
}
