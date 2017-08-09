package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 轮播图实体类
 */

public class BannerInfo {

    private String id; //id
    private String title; // 标题
    private List<String> logoPathList; //图片路径
    private String url; //web的url

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLogoPathList() {
        return logoPathList;
    }

    public void setLogoPathList(List<String> logoPathList) {
        this.logoPathList = logoPathList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
