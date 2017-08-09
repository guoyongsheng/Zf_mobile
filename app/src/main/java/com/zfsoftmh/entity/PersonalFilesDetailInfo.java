package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 个人档案详情的实体类
 */

public class PersonalFilesDetailInfo {

    private String key;
    private String value;
    private int id;

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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
