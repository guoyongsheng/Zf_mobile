package com.zfsoftmh.ui.modules.chatting.contact.add;

/**
 * Created by sy
 * on 2017/6/1.
 * <p>加好友二维码中的封装内容</p>
 */

public class FriendZxInfo {

    private String userId;
    private String showName;
    private String appKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public FriendZxInfo(String userId, String showName, String appKey) {
        this.userId = userId;
        this.showName = showName;
        this.appKey = appKey;
    }
}
