package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/6/21
 * @Description: 我的收藏列表
 */

public class FavouritesInfo {
    public boolean isOvered;
    public List<FavouritesListInfo> entityList;

    public FavouritesInfo() {
    }

    public FavouritesInfo(boolean isOvered, List<FavouritesListInfo> entityList) {
        this.isOvered = isOvered;
        this.entityList = entityList;
    }
}
