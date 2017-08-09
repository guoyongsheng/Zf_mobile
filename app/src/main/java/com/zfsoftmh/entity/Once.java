package com.zfsoftmh.entity;

import java.io.Serializable;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 保存用户是否是第一次登录的实例
 */
public class Once implements Serializable {

    private boolean isFirstTimeIn = true;

    public boolean isFirstTimeIn() {
        return isFirstTimeIn;
    }

    public void setFirstTimeIn(boolean firstTimeIn) {
        isFirstTimeIn = firstTimeIn;
    }
}
