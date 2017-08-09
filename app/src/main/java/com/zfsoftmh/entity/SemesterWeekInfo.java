package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-6-30
 * @Description: 学期周的实体类
 */

public class SemesterWeekInfo {

    private String time; //时间 格式 yyyy年MM月dd日
    private String lunar; //农历
    private String week; //周几
    private String semesterWeek; //学期周

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSemesterWeek() {
        return semesterWeek;
    }

    public void setSemesterWeek(String semesterWeek) {
        this.semesterWeek = semesterWeek;
    }
}
