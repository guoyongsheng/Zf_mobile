package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 是否是新版本的实体类
 */

public class IsNewVersion {

    private boolean isNewVersion = true; //是否是新版本 默认是新版本

    public boolean isNewVersion() {
        return isNewVersion;
    }

    public void setNewVersion(boolean newVersion) {
        isNewVersion = newVersion;
    }
}
