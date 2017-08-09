package com.zfsoftmh.common.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 时间相关的工具类
 */

public class DateUtils {

    private static final String TAG = "DateUtils";
    private static final String FORMAT = "yyyy-MM-dd";

    private static final String FORMAT_YEAR = "yyyy年MM月";

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {

    }


    /**
     * 时间格式转换 把yyyy-MM-dd HH:mm格式----------->   yyyy--MM--dd
     *
     * @param time 时间 格式是: yyyy-MM-dd HH:mm
     * @return String类型
     */
    public static String parseTime(String time) {

        if (time == null) {
            return null;
        }

        Date date = stringToDate(time, FORMAT);
        return parseDate(dateToString(date));
    }

    public static String getFullTimeStr(Calendar calendar){
        String ss = "";
        if (calendar != null) {
            ss = longToStr(calendar.getTimeInMillis(), FORMAT_DATETIME);
        }
        return ss;
    }

    private static String longToStr(long time, String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(time);
        return dataFormat.format(date);
    }


    public static long parseLong(String time){

        if (time == null) {
            return 0;
        }

        Date date = stringToDate(time, FORMAT_YEAR);
        if(date == null){
            return 0;
        }
        return date.getTime();
    }


    //String类型转换为日期
    static Date stringToDate(String time, String format) {

        if (time == null) {
            return null;
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " stringToDate " + e.getMessage());
        }
        return null;
    }

    //日期转毫秒数
    static long dataToLong(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    //日期转化为String
    private static String dateToString(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dataFormat = new SimpleDateFormat(FORMAT);
        Date dates = new Date();
        dates.setTime(calendar.getTimeInMillis());
        return dataFormat.format(dates);
    }

    //把yyyy--MM-dd转化为2014年12月
    private static String parseDate(String value) {
        if (value == null) {
            return null;
        }

        String formatValue = null;
        if (value.length() > 6) {
            formatValue = value.substring(0, 4) + "年" + value.substring(5, 7) + "月";
        }

        return formatValue;
    }

}
