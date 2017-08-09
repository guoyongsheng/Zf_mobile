package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 通讯录详情的实体
 */

public class ContactsDetailInfo {


    private boolean isPhone;
    private String key;
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }
}
