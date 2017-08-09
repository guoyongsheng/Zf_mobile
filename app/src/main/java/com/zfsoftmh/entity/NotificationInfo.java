package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-5-26
 * @Description: 通知的实体类
 */

public class NotificationInfo {

    private long id; //通知的id
    private int type; //类型 0: 邮件 1：待办事宜 2: 日程

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
