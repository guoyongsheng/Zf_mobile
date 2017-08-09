package com.zfsoftmh.entity;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 积分收入明细
 */

public class IntegralIncomeItemInfo {
    public String userid; // 用户ID

    public int source; // 积分值
    public String appsource; // 积分来源(1.移动端签到，2.Web端，3.其他)

    public String createTime; // 创建时间
    public String createtimeStr; // 页面所需显示的时间格式


    public IntegralIncomeItemInfo() {
    }

    public IntegralIncomeItemInfo(String userid, int source,
                                  String appsource, String createTime,
                                  String createtimeStr) {
        this.userid = userid;
        this.source = source;
        this.appsource = appsource;
        this.createTime = createTime;
        this.createtimeStr = createtimeStr;
    }
}
