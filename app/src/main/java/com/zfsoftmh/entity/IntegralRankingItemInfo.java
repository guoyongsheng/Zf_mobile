package com.zfsoftmh.entity;

/**
 * @author wangshimei
 * @date: 17/7/26
 * @Description:
 */

public class IntegralRankingItemInfo {
    public String xm; // 姓名
    public String wjlj; // 头像路径
    public int ranking; // 排名
    public String source; // 积分数值

    public IntegralRankingItemInfo() {
    }

    public IntegralRankingItemInfo(String xm, String wjlj, int ranking, String source) {
        this.xm = xm;
        this.wjlj = wjlj;
        this.ranking = ranking;
        this.source = source;
    }

    @Override
    public String toString() {
        return "IntegralRankingItemInfo{" +
                "xm='" + xm + '\'' +
                ", wjlj='" + wjlj + '\'' +
                ", ranking=" + ranking +
                ", source='" + source + '\'' +
                '}';
    }
}
