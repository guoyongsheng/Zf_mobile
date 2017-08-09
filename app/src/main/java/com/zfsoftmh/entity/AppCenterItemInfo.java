package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017/3/28
 * @Description: 应用中心的Item实体类
 */

public class AppCenterItemInfo implements Parcelable{

    private String id;//id，唯一性标识
    private String type;//服务类型
    private String name;//名称
    private String icon;//图标
    private String url;//url
    private String androidUrl;//安卓url
    private String iosUrl;//苹果url
    private String wechatUrl;//微信url
    private String serviceCode;//服务编码
    private String apkdownUrl;//apk下载地址
    private String apkFileName;//apk文件名
    private String apkPackage;//apk包名
    private String urlScheme;//ios参数
    private String urliTunes;//ios参数
    private String procode;//系统procode编码，业务系统需要
    private String otherFlag;//第三方标识
    private String bak;//备用字段
    private String moduletype;//
    private String isCommon;//是否通用


    private int localType;
    private String localName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getApkdownUrl() {
        return apkdownUrl;
    }

    public void setApkdownUrl(String apkdownUrl) {
        this.apkdownUrl = apkdownUrl;
    }

    public String getApkFileName() {
        return apkFileName;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public String getApkPackage() {
        return apkPackage;
    }

    public void setApkPackage(String apkPackage) {
        this.apkPackage = apkPackage;
    }

    public String getUrlScheme() {
        return urlScheme;
    }

    public void setUrlScheme(String urlScheme) {
        this.urlScheme = urlScheme;
    }

    public String getUrliTunes() {
        return urliTunes;
    }

    public void setUrliTunes(String urliTunes) {
        this.urliTunes = urliTunes;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getOtherFlag() {
        return otherFlag;
    }

    public void setOtherFlag(String otherFlag) {
        this.otherFlag = otherFlag;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getModuletype() {
        return moduletype;
    }

    public void setModuletype(String moduletype) {
        this.moduletype = moduletype;
    }

    public String getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(String isCommon) {
        this.isCommon = isCommon;
    }


    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

    public int getLocalType() {
        return localType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.url);
        dest.writeString(this.androidUrl);
        dest.writeString(this.iosUrl);
        dest.writeString(this.wechatUrl);
        dest.writeString(this.serviceCode);
        dest.writeString(this.apkdownUrl);
        dest.writeString(this.apkFileName);
        dest.writeString(this.apkPackage);
        dest.writeString(this.urlScheme);
        dest.writeString(this.urliTunes);
        dest.writeString(this.procode);
        dest.writeString(this.otherFlag);
        dest.writeString(this.bak);
        dest.writeString(this.moduletype);
        dest.writeString(this.isCommon);
        dest.writeInt(this.localType);
        dest.writeString(this.localName);
    }

    public AppCenterItemInfo() {
    }

    protected AppCenterItemInfo(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.url = in.readString();
        this.androidUrl = in.readString();
        this.iosUrl = in.readString();
        this.wechatUrl = in.readString();
        this.serviceCode = in.readString();
        this.apkdownUrl = in.readString();
        this.apkFileName = in.readString();
        this.apkPackage = in.readString();
        this.urlScheme = in.readString();
        this.urliTunes = in.readString();
        this.procode = in.readString();
        this.otherFlag = in.readString();
        this.bak = in.readString();
        this.moduletype = in.readString();
        this.isCommon = in.readString();
        this.localType = in.readInt();
        this.localName = in.readString();
    }

    public static final Creator<AppCenterItemInfo> CREATOR = new Creator<AppCenterItemInfo>() {
        @Override
        public AppCenterItemInfo createFromParcel(Parcel source) {
            return new AppCenterItemInfo(source);
        }

        @Override
        public AppCenterItemInfo[] newArray(int size) {
            return new AppCenterItemInfo[size];
        }
    };
}
