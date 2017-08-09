package com.zfsoftmh.pay.entity;

/**
 * @author wesley
 * @date: 2017/4/19
 * @Description: 支付的业务参数
 */

public class BizContent {

    private String total_amount;  //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    private String subject; // 商品的标题/交易标题/订单标题/订单关键字等
    private String out_trade_no; //商户网站唯一订单号
    private String product_code = "QUICK_MSECURITY_PAY"; // 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
    private String seller_id; //

    private String timeout_express = "30m";

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getTimeout_express() {
        return timeout_express;
    }
}
