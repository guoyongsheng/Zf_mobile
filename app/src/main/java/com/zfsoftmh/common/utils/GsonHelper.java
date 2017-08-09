package com.zfsoftmh.common.utils;

import com.google.gson.Gson;
import com.zfsoftmh.ui.base.BaseApplication;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 对象转化为String String转化为对象
 */
public class GsonHelper {

    private GsonHelper() {

    }

    /**
     * 对象转化为字符串
     *
     * @param t   对象实例
     * @param <T> 对象
     * @return 字符串
     */
    public static <T> String objectToString(T t) {
        Gson gson = BaseApplication.getAppComponent().getGson();
        return gson.toJson(t);
    }

    /**
     * @param json json格式的字符串
     * @param t    要返回的对象
     * @param <T>  要返回的对象的类型
     * @return T
     */
    public static <T> T stringToObject(String json, Class<T> t) {
        Gson gson = BaseApplication.getAppComponent().getGson();
        return gson.fromJson(json, t);
    }
}
