package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/23
 * @Description: 我的门户的实体类---包括用户头像 和 MyPortalItemInfo
 */

public class MyPortalInfo {

    private String headPicturePath;   //我的头像路径
    private String source; // 积分数据
    private String isTodaySign; // 签到状态（1.已签到,0.未签到）
    private List<MyPortalItemInfo> list;  //我的门户的item

    public String getHeadPicturePath() {
        return headPicturePath;
    }

    public void setHeadPicturePath(String headPicturePath) {
        this.headPicturePath = headPicturePath;
    }

    public List<MyPortalItemInfo> getList() {
        return list;
    }

    public void setList(List<MyPortalItemInfo> list) {
        this.list = list;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsTodaySign() {
        return isTodaySign;
    }

    public void setIsTodaySign(String isTodaySign) {
        this.isTodaySign = isTodaySign;
    }
}
