package com.zfsoftmh.entity;

import io.realm.RealmObject;

/**
 * @author wesley
 * @date: 2017-5-26
 * @Description:
 */

public class RealmIntegerInfo extends RealmObject {

    private long notificationId; //通知的id

    public RealmIntegerInfo(){

    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getNotificationId() {
        return notificationId;
    }
}
