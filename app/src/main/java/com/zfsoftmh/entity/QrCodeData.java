package com.zfsoftmh.entity;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 二维码的实体类
 */

public class QrCodeData {

    /**
     * @param qrCode 对应码
     * {@link ZxCode}
     */
    public QrCodeData(int qrCode){
        this.code = qrCode;
    }

    private int code;
    private String content;
    private String callback;

    public int getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getCallback() {
        return callback;
    }
}
