package com.zfsoftmh.common.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author wesley
 * @date: 2017/3/20
 * @Description: 给所有的请求参数加密的工具类
 */
public class CodeUtil {

    private static final String TAG = "CodeUtil";

    private CodeUtil() {

    }

    private final static String encryptKey = "zfsoft";

    /**
     * 向量
     */
    private final static String iv = "76543210";
    /**
     * 编码
     */
    private final static String encoding = "utf-8";

    /**
     * 加密
     *
     * @param plainText 要加密的value值
     * @param app_token 用户登录凭证
     * @return 加密后的数据
     */
    private static String encode(String plainText, String app_token) {
        if (app_token == null) {
            LoggerHelper.e(TAG, " encode 参数加密失败: " + " app_token = " + "    " + null);
            return "";
        }
        if (plainText == null) {
            plainText = "";
        }
        Key key;
        DESedeKeySpec spec;
        try {
            spec = new DESedeKeySpec(app_token.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            key = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, key, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            return Base64Util.encode(encryptData);
        } catch (Exception e) {
            LoggerHelper.e(TAG, " encode 参数加密失败: " + e.getMessage());
            return "";
        }
    }

    /**
     * 解密
     */
    public static String decode(String encryptText, String secretKey) throws Exception {
        Key desKey;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        desKey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));
        return new String(decryptData, encoding);
    }


    /**
     * 获取加密数据
     *
     * @param plainText 要加密的value
     * @param app_token 用户登录的token
     * @return 加密后的数据
     */
    public static String getEncodedValueWithToken(String plainText, String app_token) {

        return encode(plainText, app_token);
        /*try {
            return java.net.URLEncoder.encode(encode(plainText, app_token), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getEncodedValueWithToken 数据加密失败 " + e.getMessage());
        }
        return "";*/
    }


    /**
     * 获取加密数据
     *
     * @param value 要加密的value
     * @return 加密后的数据
     */
    public static String getEncodedValue(String value) {

        return value;
       /* try {
            return java.net.URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getEncodedValue 数据加密失败 " + e.getMessage());
        }
        return "";*/
    }
}
