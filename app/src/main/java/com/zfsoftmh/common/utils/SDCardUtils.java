package com.zfsoftmh.common.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author wesley
 * @date: 2017/3/27
 * @Description: sdcard 工具类
 */

public class SDCardUtils {

    private SDCardUtils() {

    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用  false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获取SD卡路径
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }
}
