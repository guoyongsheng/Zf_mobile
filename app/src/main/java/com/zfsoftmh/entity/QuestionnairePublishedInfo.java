package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description: 已发布问卷调查的实体类
 */

public class QuestionnairePublishedInfo {

    private String papermainid; //id
    private String papermainname; //标题
    private String instruction;//简介
    private String creater; //创建者
    private String createtimeStr; //时间
    private String qn_owner; // 0：都可见 1：仅自己可见
    private String resultUrl;
    private String flag; //0: 没有参与过 1: 参与过
    private String starttimeStr; //开始时间
    private String endtimeStr; //结束时间
    private List<QuestionnaireQuestionInfo> questionList; //问题集合

    private int tag;

    public String getPapermainid() {
        return papermainid;
    }

    public void setPapermainid(String papermainid) {
        this.papermainid = papermainid;
    }

    public String getPapermainname() {
        return papermainname;
    }

    public void setPapermainname(String papermainname) {
        this.papermainname = papermainname;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreatetimeStr() {
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public String getQn_owner() {
        return qn_owner;
    }

    public void setQn_owner(String qn_owner) {
        this.qn_owner = qn_owner;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<QuestionnaireQuestionInfo> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionnaireQuestionInfo> questionList) {
        this.questionList = questionList;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }


    public void setStarttimeStr(String starttimeStr) {
        this.starttimeStr = starttimeStr;
    }

    public String getStarttimeStr() {
        return starttimeStr;
    }

    public void setEndtimeStr(String endtimeStr) {
        this.endtimeStr = endtimeStr;
    }

    public String getEndtimeStr() {
        return endtimeStr;
    }
}
