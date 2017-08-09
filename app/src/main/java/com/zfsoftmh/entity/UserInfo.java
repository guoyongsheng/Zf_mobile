package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/3/20
 * @Description:
 */

public class UserInfo {



    private String imei;
    private String imsi;
    private String sysinfo;
    private String ua;
    private String phonum;
    private String v;
    private String account;

    public UserInfo(String imei, String imsi, String sysinfo, String ua, String phonum, String v, String account) {
        this.imei = imei;
        this.imsi = imsi;
        this.sysinfo = sysinfo;
        this.ua = ua;
        this.phonum = phonum;
        this.v = v;
        this.account = account;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSysinfo() {
        return sysinfo;
    }

    public void setSysinfo(String sysinfo) {
        this.sysinfo = sysinfo;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getPhonum() {
        return phonum;
    }

    public void setPhonum(String phonum) {
        this.phonum = phonum;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
}
