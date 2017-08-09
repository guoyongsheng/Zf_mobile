package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 失物招领的实体类
 */

public class LostAndFoundItemInfo {

    private String id; //id
    private String username; //工号
    private String name; //用户名
    private String title; //标题
    private String content; //内容
    private String timecreatestr; //创建时间
    private String detaiURL; //网址
    private int flag; //标志位 1--失物招领 我捡东西了  0--寻物启事 我丟东西了
    private int ispass; //0代表为审核过，1代表未审核通过，2代表已审核通过

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTimecreatestr() {
        return timecreatestr;
    }

    public void setTimecreatestr(String timecreatestr) {
        this.timecreatestr = timecreatestr;
    }

    public String getDetaiURL() {
        return detaiURL;
    }

    public void setDetaiURL(String detaiURL) {
        this.detaiURL = detaiURL;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setIspass(int ispass) {
        this.ispass = ispass;
    }

    public int getIspass() {
        return ispass;
    }
}
