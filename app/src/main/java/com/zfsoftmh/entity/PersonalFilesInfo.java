package com.zfsoftmh.entity;

import java.util.List;
import java.util.Map;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 个人档案的实体类
 */

public class PersonalFilesInfo {

    private String informationid;  //个人信息类别id
    private String informationname; //个人信息类别名称
    private String informationico; //个人信息类别LOG图
    private List<List<Map<String, String>>> informationList; //详情

    private boolean isChecked; //是否选中

    public String getInformationid() {
        return informationid;
    }

    public void setInformationid(String informationid) {
        this.informationid = informationid;
    }

    public String getInformationname() {
        return informationname;
    }

    public void setInformationname(String informationname) {
        this.informationname = informationname;
    }

    public String getInformationico() {
        return informationico;
    }

    public void setInformationico(String informationico) {
        this.informationico = informationico;
    }


    public void setInformationList(List<List<Map<String, String>>> informationList) {
        this.informationList = informationList;
    }

    public List<List<Map<String, String>>> getInformationList() {
        return informationList;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
