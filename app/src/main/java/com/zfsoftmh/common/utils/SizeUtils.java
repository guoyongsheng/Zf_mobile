package com.zfsoftmh.common.utils;

import android.content.Context;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 尺寸相关的工具类
 */

public class SizeUtils {

    private static final String TAG = "SizeUtils";

    private SizeUtils() {
        try {
            throw new UnsupportedOperationException("u can't instantiate me...");
        } catch (Exception e) {
            LoggerHelper.e(TAG, " SizeUtils " + e.getMessage());
        }
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue, Context context) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue, Context context) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
