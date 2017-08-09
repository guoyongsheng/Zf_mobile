package com.zfsoftmh.pay.entity;

/**
 * @author wesley
 * @date: 2017/4/19
 * @Description: 签名信息的实体类
 */

public class SignInfo {

    private String app_id;// id
    private BizContent biz_content;// 业务参数
    private String charset;// 编码方式
    private String method;// 方法名
    private String sign_type;// 加密类型
    private String notify_url;// 回调url
    private String timestamp;// 请求开始时间
    private String version;// 版本号
    private String sign;// 签名
    //private String orderInfoEncoded;


    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public BizContent getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(BizContent biz_content) {
        this.biz_content = biz_content;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /*public void setOrderInfoEncoded(String orderInfoEncoded) {
        this.orderInfoEncoded = orderInfoEncoded;
    }

    public String getOrderInfoEncoded() {
        return orderInfoEncoded;
    }*/
}
