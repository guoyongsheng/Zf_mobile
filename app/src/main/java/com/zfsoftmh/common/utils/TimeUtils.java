package com.zfsoftmh.common.utils;

import android.content.ContentResolver;
import android.content.Context;

import com.jeek.calendar.widget.calendar.LunarCalendarUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author wesley
 * @date: 2017-7-3
 * @Description: 时间的工具类
 */

public class TimeUtils {

    private static final String TAG = "TimeUtils";
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private TimeUtils() {
        try {
            throw new UnsupportedOperationException("u can't instantiate me...");
        } catch (Exception e) {
            LoggerHelper.e(TAG, "TimeUtils " + e.getMessage());
        }
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }


    /**
     * 将时间字符串转为时间戳
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " string2Millis " + e.getMessage());
        }
        return -1;
    }


    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }


    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " string2Date " + e.getMessage());
        }
        return null;
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为format</p>
     *
     * @param date   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }


    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 获取中式星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 中式星期
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取中式星期
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 中式星期
     */
    public static String getChineseWeek(final String time, final DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 获取中式星期
     *
     * @param date Date类型时间
     * @return 中式星期
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 获取中式星期
     *
     * @param millis 毫秒时间戳
     * @return 中式星期
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     */
    public static int getWeekIndex(final String time) {
        return getWeekIndex(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(final String time, final DateFormat format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     */
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     */
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }


    /**
     * 获取星期
     *
     * @param index 星期索引
     * @return 星期数
     */
    public static String getWeek(int index) {
        int newIndex = index - 1;
        String[] week = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if (newIndex >= 0 && week.length > newIndex) {
            return week[newIndex];
        }

        return "";
    }


    /**
     * 公历转农历
     *
     * @param currentTime 时间戳
     * @return 农历
     */
    public static String solarToLunar(long currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(millis2Date(currentTime));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LunarCalendarUtils.Solar solar = new LunarCalendarUtils.Solar(year, month + 1, day);
        LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(solar);
        int lunarDay = lunar.lunarDay;
        int lunayMonth = lunar.lunarMonth;
        return number2UpCaseNumber(lunayMonth) + "月" + LunarCalendarUtils.getLunarDayString(lunarDay);
    }

    /**
     * 判断手机时间是否是24进制
     *
     * @param context 上下文对象
     * @return true: 是 false: 不是
     */
    public static boolean is24(Context context) {
        if (context == null) {
            return false;
        }
        ContentResolver cv = context.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);
        return strTimeFormat != null && strTimeFormat.equals("24");
    }


    //数字转大写
    private static String number2UpCaseNumber(int number) {

        switch (number) {
        case 1:
            return "一";

        case 2:
            return "二";

        case 3:
            return "三";

        case 4:
            return "四";

        case 5:
            return "五";

        case 6:
            return "六";

        case 7:
            return "七";

        case 8:
            return "八";

        case 9:
            return "九";

        case 10:
            return "十";

        case 11:
            return "十一";

        case 12:
            return "腊";

        default:
            return "";
        }
    }

}
