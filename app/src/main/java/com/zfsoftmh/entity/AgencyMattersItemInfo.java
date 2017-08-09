package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 待办事宜的实体类
 */

public class AgencyMattersItemInfo implements Parcelable {

    private String id;//待办事宜id
    private String title;//标题
    private String time;//日期
    private String createUser;//起草人
    private String type;//类型
    private String tablename;//学校名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.createUser);
        dest.writeString(this.type);
        dest.writeString(this.tablename);
    }

    public AgencyMattersItemInfo() {
    }

    protected AgencyMattersItemInfo(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.time = in.readString();
        this.createUser = in.readString();
        this.type = in.readString();
        this.tablename = in.readString();
    }

    public static final Creator<AgencyMattersItemInfo> CREATOR = new Creator<AgencyMattersItemInfo>() {
        @Override
        public AgencyMattersItemInfo createFromParcel(Parcel source) {
            return new AgencyMattersItemInfo(source);
        }

        @Override
        public AgencyMattersItemInfo[] newArray(int size) {
            return new AgencyMattersItemInfo[size];
        }
    };
}
