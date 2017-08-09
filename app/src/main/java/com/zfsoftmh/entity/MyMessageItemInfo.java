package com.zfsoftmh.entity;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public class MyMessageItemInfo {

    /**
     * tsid : 4CD252C46F920374E0538513470A816C
     * tssj : Apr 10, 2017 11:06:44 PM
     * tsnr : 测试
     * tsry : 管理员
     * tsfs : 1
     * tspt : ALL
     * tsdxlx : ALL
     * tsjg : {"msg_id":912136166,"sendno":1494502654}
     * timeToLive : 86400
     * appType : YDXY
     * tssjStr : 2017-04-10 23:06
     */

    public String tsid;
    public String tssj;
    public String tsnr; // 推送标题
    public String tsry; // 发件人
    public String tsfs; // 推送方式 0.消息 1.通知
    public String tspt;
    public String tsdxlx;
    public String tsjg;
    public int timeToLive;
    public String appType;
    public String tssjStr; // 推送时间
    public String extrasStr;
    public String func_type; // 判断图标类型(302是邮件 306是待办)
    public String xtbm; // 判断是jw还是oa类型web

    public MyMessageItemInfo() {
    }

    public MyMessageItemInfo(String tsid, String tssj, String tsnr, String tsry,
                             String tsfs, String tspt, String tsdxlx, String tsjg,
                             int timeToLive, String appType, String tssjStr,
                             String extrasStr, String func_type, String xtbm) {
        this.tsid = tsid;
        this.tssj = tssj;
        this.tsnr = tsnr;
        this.tsry = tsry;
        this.tsfs = tsfs;
        this.tspt = tspt;
        this.tsdxlx = tsdxlx;
        this.tsjg = tsjg;
        this.timeToLive = timeToLive;
        this.appType = appType;
        this.tssjStr = tssjStr;
        this.extrasStr = extrasStr;
        this.func_type = func_type;
        this.xtbm = xtbm;
    }

    @Override
    public String toString() {
        return "MyMessageItemInfo{" +
                "tsid='" + tsid + '\'' +
                ", tssj='" + tssj + '\'' +
                ", tsnr='" + tsnr + '\'' +
                ", tsry='" + tsry + '\'' +
                ", tsfs='" + tsfs + '\'' +
                ", tspt='" + tspt + '\'' +
                ", tsdxlx='" + tsdxlx + '\'' +
                ", tsjg='" + tsjg + '\'' +
                ", timeToLive=" + timeToLive +
                ", appType='" + appType + '\'' +
                ", tssjStr='" + tssjStr + '\'' +
                ", extrasStr='" + extrasStr + '\'' +
                ", func_type='" + func_type + '\'' +
                ", xtbm='" + xtbm + '\'' +
                '}';
    }
}
