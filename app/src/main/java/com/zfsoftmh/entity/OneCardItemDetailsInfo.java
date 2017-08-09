package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 一卡通的详情实体
 */

public class OneCardItemDetailsInfo {

    private String consumeAspect;//消费地点
    private String consumetime;//消费时间
    private String balance;//消费之后一卡通余额
    private String outlay;//消费金额

    private int headerId; //头部id
    private String header;

    public String getConsumeAspect() {
        return consumeAspect;
    }

    public void setConsumeAspect(String consumeAspect) {
        this.consumeAspect = consumeAspect;
    }

    public String getConsumetime() {
        return consumetime;
    }

    public void setConsumetime(String consumetime) {
        this.consumetime = consumetime;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOutlay() {
        return outlay;
    }

    public void setOutlay(String outlay) {
        this.outlay = outlay;
    }


    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }


    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public int getHeaderId() {
        return headerId;
    }
}
