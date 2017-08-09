package com.zfsoftmh.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wesley
 * @date: 2017/5/4
 * @Description: 正则表达式的工具类
 */

class RegularUtils {

    private RegularUtils() {

    }


    /**
     * 判断是否是大写的26个英文字母
     *
     * @param value 要判断的值
     * @return true: 是  false: 不是
     */
    static boolean isUpperCaseChar(String value) {
        String regular = "^[A-Z]+$";
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}
