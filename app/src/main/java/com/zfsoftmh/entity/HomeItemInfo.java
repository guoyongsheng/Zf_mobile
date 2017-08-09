package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 首页的实体类
 */

public class HomeItemInfo {

    private long id;
    private int type; //类型 2: 显示item标题的布局 3: 显示item的布局
    private String title; //标题
    private String content; //内容
    private String time; //时间


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
