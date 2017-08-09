package com.zfsoftmh.pay.util;

import com.zfsoftmh.pay.entity.SignInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderInfoUtil2_0 {

    /**
     * 构造支付订单参数列表
     *
     * @param app_id      应用id
     * @param method      方法
     * @param time        请求时间
     * @param biz_content 业务参数 json格式的字符串
     * @return 单参数列表
     */
    public static Map<String, String> buildOrderParamMap(String app_id, String method, String time,
            String biz_content) {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("app_id", app_id);
        keyValues.put("method", method);
        keyValues.put("charset", "utf-8");
        keyValues.put("sign_type", "RSA");
        keyValues.put("timestamp", time);
        keyValues.put("version", "1.0");
        keyValues.put("notify_url", "http://demo.zfsoft.com/ALiPay/servlet/NotifyServlet/getSign");
        keyValues.put("biz_content", biz_content);
        return keyValues;
    }


    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return 加密后的请求参数
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        StringBuilder sb = new StringBuilder();
        int size = keys.size() - 1;
        for (int i = 0; i < size; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(size);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 构建请求参数
     *
     * @param signInfo 对象
     * @return 请求参数
     */
    public static String buildOrderParam(SignInfo signInfo, String biz_content) {
        if (signInfo == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String app_id = signInfo.getApp_id();
        stringBuilder.append(buildKeyValue("app_id", app_id, true)).append("&");
        //String biz_content = signInfo.getBiz_content();
        stringBuilder.append(buildKeyValue("biz_content", biz_content, true)).append("&");
        String charset = signInfo.getCharset();
        stringBuilder.append(buildKeyValue("charset", charset, true)).append("&");
        String method = signInfo.getMethod();
        stringBuilder.append(buildKeyValue("method", method, true)).append("&");
        String sign_type = signInfo.getSign_type();
        stringBuilder.append(buildKeyValue("sign_type", sign_type, true)).append("&");
        String notify_url = signInfo.getNotify_url();
        stringBuilder.append(buildKeyValue("notify_url", notify_url, true)).append("&");
        String timestamp = signInfo.getTimestamp();
        stringBuilder.append(buildKeyValue("timestamp", timestamp, true)).append("&");
        String version = signInfo.getVersion();
        stringBuilder.append(buildKeyValue("version", version, true)).append("&");
        String sign = signInfo.getSign();
        stringBuilder.append(sign);
        return stringBuilder.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key      key
     * @param value    value
     * @param isEncode 是否加密
     * @return 拼接后的值
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 要求外部订单号必须唯一。
     *
     * @return 订单号
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        if(key.length() > 15){
            return key.substring(0, 15);
        }
        return key;
    }

}
