package com.zfsoftmh.common.utils;

import android.app.Activity;
import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

/**
 * @author wesley
 * @date: 2017-7-20
 * @Description: 沉浸式状态栏的工具类
 */

public class ImmersionStatusBarUtils {

    private static final String TAG = "ImmersionStatusBarUtils";

    private ImmersionStatusBarUtils() {

    }

    /**
     * 实现沉浸式状态栏---activity
     *
     * @param activity           当前界面
     * @param statusBarColor     状态栏颜色
     * @param navigationBarColor 导航栏颜色
     * @param view               标题栏或者是toolbar---高度不能是wrap_content
     * @param isDarkFont         状态栏字体颜色是否是深色 android M 可用
     * @param statusAlpha        当手机版本 < android M 时,该字段可用
     */
    public static void initStatusBarInActivity(Activity activity, int statusBarColor,
            int navigationBarColor, View view, boolean isDarkFont, @FloatRange(from = 0f, to = 1f)
            float statusAlpha, boolean keyBoardEnable, ImmersionBar immersionBar) {

        if (activity == null || view == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInActivity " + " 沉浸式设置失败");
            return;
        }

        immersionBar
                .statusBarColor(statusBarColor)
                .titleBar(view)
                .keyboardEnable(keyBoardEnable)
                .init();
    }


    /**
     * 实现沉浸式状态栏---fragment
     *
     * @param fragment           当前界面
     * @param statusBarColor     状态栏颜色
     * @param navigationBarColor 导航栏颜色
     * @param view               标题栏或者是toolbar---高度不能是wrap_content
     * @param isDarkFont         状态栏字体颜色是否是深色 android M 可用
     * @param statusAlpha        当手机版本 < android M 时,该字段可用
     */
    public static void initStatusBarInFragment(Fragment fragment, int statusBarColor,
            int navigationBarColor, View view, boolean isDarkFont, @FloatRange(from = 0f, to = 1f)
            float statusAlpha, ImmersionBar immersionBar) {


        if (fragment == null || view == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInFragment " + " 沉浸式设置失败");
            return;
        }

        immersionBar
                .statusBarColor(statusBarColor)
                .titleBar(view)
                .init();
    }


    /**
     * 实现沉浸式状态栏---fragment
     *
     * @param fragment           当前界面
     * @param navigationBarColor 导航栏颜色
     * @param isDarkFont         状态栏字体颜色是否是深色 android M 可用
     * @param statusAlpha        当手机版本 < android M 时,该字段可用
     */
    public static void initStatusBarInFragment(Fragment fragment, int navigationBarColor,
            boolean isDarkFont, @FloatRange(from = 0f, to = 1f)
            float statusAlpha, ImmersionBar immersionBar) {

        if (fragment == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInFragment " + " 沉浸式设置失败");
            return;
        }

        immersionBar.transparentStatusBar().init();
    }


    /**
     * 实现沉浸式状态栏---fragment
     *
     * @param fragment           当前界面
     * @param statusBarColor     状态栏颜色
     * @param navigationBarColor 导航栏颜色
     * @param view               标题栏或者是toolbar---高度不能是wrap_content
     * @param isDarkFont         状态栏字体颜色是否是深色 android M 可用
     * @param statusAlpha        当手机版本 < android M 时,该字段可用
     */
    public static void initStatusBarInFragmentWithStatusBarView(Fragment fragment, int statusBarColor,
            int navigationBarColor, View view, boolean isDarkFont, @FloatRange(from = 0f, to = 1f)
            float statusAlpha, ImmersionBar immersionBar) {


        if (fragment == null || view == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInFragmentWithStatusBarView " + " 沉浸式设置失败");
            return;
        }

        immersionBar
                .statusBarColor(statusBarColor)
                .statusBarView(view)
                .init();

    }


    public static void initStatusBarInFragmentWithSupport(Fragment fragment, int statusBarColor,
            int navigationBarColor, View view, boolean isDarkFont, @FloatRange(from = 0f, to = 1f)
            float statusAlpha, ImmersionBar immersionBar) {


        if (fragment == null || view == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInFragmentWithSupport " + " 沉浸式设置失败");
            return;
        }

        immersionBar
                .addViewSupportTransformColor(view, statusBarColor)
                .statusBarAlpha(statusAlpha)
                .init();


    }


    public static void initStatusBarInHomeFragment(View view, ImmersionBar immersionBar) {

        if (view == null || immersionBar == null) {
            LoggerHelper.e(TAG, " initStatusBarInHomeFragment " + " 沉浸式设置失败");
            return;
        }

        immersionBar.titleBar(view, false).init();
    }


    public static void reset(ImmersionBar immersionBar){

        if(immersionBar == null){
            return;
        }

        immersionBar.reset();
    }
}
