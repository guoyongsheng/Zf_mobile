package com.zfsoftmh.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 日程管理的实体类
 */

public class ScheduleManagementInfo extends RealmObject {

    @PrimaryKey
    private long id;
    private String title; //标题
    private String start_time; //开始时间 格式：yyyy-MM-dd HH:mm:ss
    private String end_time; //结束时间 格式：yyyy-MM-dd HH:mm:ss
    private int hint_type; //提醒类型 0：提前10分钟提醒 1：提前30分钟提醒 2：提前5分钟提醒 3：准时提醒 4：不提醒
    private int repeat_type; //重复类型 0：提醒1次 1：每5分钟提醒一次 2：每10分钟提醒一次 3：每15分钟提醒一次
    private String head_time; //时间 格式： yyyy年MM月
    private String time; //时间 格式: yyyy-MM-dd
    private RealmList<RealmIntegerInfo> notificationIdList; //通知的id集合

    public ScheduleManagementInfo(){

    }

    @Ignore
    private int headerId; //头部的id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getHint_type() {
        return hint_type;
    }

    public void setHint_type(int hint_type) {
        this.hint_type = hint_type;
    }

    public int getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(int repeat_type) {
        this.repeat_type = repeat_type;
    }

    public String getHead_time() {
        return head_time;
    }

    public void setHead_time(String head_time) {
        this.head_time = head_time;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setNotificationIdList(RealmList<RealmIntegerInfo> notificationIdList) {
        this.notificationIdList = notificationIdList;
    }

    public RealmList<RealmIntegerInfo> getNotificationIdList() {
        return notificationIdList;
    }
}
