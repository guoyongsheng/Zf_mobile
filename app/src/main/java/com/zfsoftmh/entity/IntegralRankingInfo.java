package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/26
 * @Description: 积分排名
 */

public class IntegralRankingInfo {
    public String ranking; // 个人积分排名
    public List<IntegralRankingItemInfo> itemList;

    public IntegralRankingInfo() {
    }

    public IntegralRankingInfo(String ranking, List<IntegralRankingItemInfo> itemList) {
        this.ranking = ranking;
        this.itemList = itemList;
    }
}
