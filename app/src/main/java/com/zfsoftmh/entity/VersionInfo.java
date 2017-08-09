package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 版本信息实体类
 */

public class VersionInfo implements Parcelable {

    private String url; //下载地址
    private String prompt; //跟新信息
    private String updateTpye; // 1: 强制更新  2：非强制更新 3：不需要更新
    private String serveAddress;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getUpdateTpye() {
        return updateTpye;
    }

    public void setUpdateTpye(String updateTpye) {
        this.updateTpye = updateTpye;
    }

    public String getServeAddress() {
        return serveAddress;
    }

    public void setServeAddress(String serveAddress) {
        this.serveAddress = serveAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.prompt);
        dest.writeString(this.updateTpye);
        dest.writeString(this.serveAddress);
    }

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        this.url = in.readString();
        this.prompt = in.readString();
        this.updateTpye = in.readString();
        this.serveAddress = in.readString();
    }

    public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
        @Override
        public VersionInfo createFromParcel(Parcel source) {
            return new VersionInfo(source);
        }

        @Override
        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };
}
