package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description: 问卷调查问题的实体类
 */

public class QuestionnaireQuestionInfo implements Parcelable{

    private String questionid; //问题id
    private String title; //标题
    private String type; //类型 0: 单选题 1: 多选题 2: 简答题 3: 打分题

    private String result; //结果

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionid);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(result);
    }

    private QuestionnaireQuestionInfo(Parcel in) {
        questionid = in.readString();
        title = in.readString();
        type = in.readString();
        result = in.readString();
    }

    public static final Creator<QuestionnaireQuestionInfo> CREATOR = new Creator<QuestionnaireQuestionInfo>() {
        @Override
        public QuestionnaireQuestionInfo createFromParcel(Parcel in) {
            return new QuestionnaireQuestionInfo(in);
        }

        @Override
        public QuestionnaireQuestionInfo[] newArray(int size) {
            return new QuestionnaireQuestionInfo[size];
        }
    };
}
