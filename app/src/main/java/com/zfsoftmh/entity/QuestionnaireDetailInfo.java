package com.zfsoftmh.entity;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 问卷调查详情实体类
 */

public class QuestionnaireDetailInfo extends RealmObject {

    @PrimaryKey
    private long id; //主鍵

    @Expose
    private int type; //类型 0: 单选题 1: 多选题 2: 简答题 3: 打分题

    @Expose
    private String title; //标题

    @Expose
    private String selection = "";

    @Expose
    private int maxSel;

    @Ignore
    private int tag;

    public QuestionnaireDetailInfo(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setMaxSel(int maxSel) {
        this.maxSel = maxSel;
    }

    public int getMaxSel() {
        return maxSel;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }
}
