package com.zfsoftmh.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 问卷调查的实体类
 */

public class QuestionnaireItemInfo extends RealmObject {

    @PrimaryKey
    private long id; //主鍵

    @Expose
    @SerializedName("qn_name")
    @Required
    private String title; //标题

    @Expose
    @SerializedName("qn_intro")
    private String description; //描述

    @Expose
    private String qn_marking; //备注
    private String time; //时间

    @Expose
    private int qn_owner; // 0:不是仅对自己可见 1:仅对自己可见

    @Expose
    @SerializedName("topics")
    private RealmList<QuestionnaireDetailInfo> list;

    @Ignore
    private int tag; //标志位 0: 未点击  1: 点击

    public QuestionnaireItemInfo(){

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setList(RealmList<QuestionnaireDetailInfo> list) {
        this.list = list;
    }

    public RealmList<QuestionnaireDetailInfo> getList() {
        return list;
    }

    public void setQn_marking(String qn_marking) {
        this.qn_marking = qn_marking;
    }

    public String getQn_marking() {
        return qn_marking;
    }

    public void setQn_owner(int qn_owner) {
        this.qn_owner = qn_owner;
    }

    public int getQn_owner() {
        return qn_owner;
    }
}
