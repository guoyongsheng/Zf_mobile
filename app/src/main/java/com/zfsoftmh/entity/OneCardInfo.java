package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 一卡通的实体类
 */

public class OneCardInfo {

    private String cardNumber;//卡号
    private String cardBlance;//余额

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardBlance() {
        return cardBlance;
    }

    public void setCardBlance(String cardBlance) {
        this.cardBlance = cardBlance;
    }
}
