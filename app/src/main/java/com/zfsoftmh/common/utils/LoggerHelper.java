package com.zfsoftmh.common.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zfsoftmh.BuildConfig;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 打印日志的工具类
 */
public class LoggerHelper {

    private LoggerHelper() {

    }

    /**
     * 初始化logger debug版本打印日志 release版本不打印日志
     *
     * @param tag 默认显示包名
     */
    public static void init(String tag) {
        Logger.init(tag).logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
    }

    /**
     * 打印日志
     *
     * @param msg 要打印的信息
     */
    public static void i(String msg) {
        Logger.i(msg);
    }

    /**
     * 打印日志
     *
     * @param tag 自定义tag
     * @param msg 要打印的信息
     */
    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    /**
     * 打印日志
     *
     * @param msg 要打印的信息
     */
    public static void d(String msg) {
        Logger.d(msg);
    }

    /**
     * 打印日志
     *
     * @param tag 自定义tag
     * @param msg 要打印的信息
     */
    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    /**
     * 打印日志
     *
     * @param msg 要打印的信息
     */
    public static void e(String msg) {
        Logger.e(msg);
    }

    /**
     * 打印日志
     *
     * @param tag 自定义tag
     * @param msg 要打印的信息
     */
    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    /**
     * 打印日志
     *
     * @param msg 要打印的信息---json格式的
     */
    public static void json(String msg) {
        Logger.json(msg);
    }

    /**
     * 打印日志
     *
     * @param tag 自定义tag
     * @param msg 要打印的信息---json格式的
     */
    public static void json(String tag, String msg) {
        Logger.t(tag).json(msg);
    }
}
