package com.zfsoftmh.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.IsLogin;
import com.zfsoftmh.entity.IsNewVersion;
import com.zfsoftmh.entity.Once;
import com.zfsoftmh.entity.User;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 数据存储的工具类
 */
public class DbHelper {

    private static final String TAG = "DbHelper";

    private DbHelper() {

    }

    /**
     * 保存对象到SharedPreferences中
     *
     * @param context 上下文对象
     * @param name    文件名称
     * @param key     保存对象的key值
     * @param t       要保存的对象
     * @param <T>     对象
     */
    public static <T> void saveValueBySharedPreferences(Context context, String name,
            String key, T t) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, GsonHelper.objectToString(t));
        editor.apply();
    }

    /**
     * 从SharedPreferences中获取对象
     *
     * @param context 上下文对象
     * @param name    文件名称
     * @param key     保存对象的key值
     * @param t       要获取的对象
     * @param <T>     对象
     * @return T 具体的对象
     */
    public static <T> T getValueBySharedPreferences(Context context, String name, String key,
            Class<T> t) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return GsonHelper.stringToObject(value, t);
    }

    /**
     * 判断用户是否是第一次进入
     *
     * @param context 上下文对象
     * @return true: 第一次进入  false: 不是第一次进入
     */
    public static boolean checkUserIsFirstTimeIn(Context context) {
        if (context == null) {
            return true;
        }
        Once once = getValueBySharedPreferences(context, Config.DB.IS_FIRST_TIME_IN_NAME,
                Config.DB.IS_FIRST_TIME_IN_KEY, Once.class);
        return once == null || once.isFirstTimeIn();
    }

    /**
     * 获取用户的对象---用户登录时返回的信息
     *
     * @param context 上下文对象
     * @return user
     */
    public static User getUserInfo(Context context) {
        if (context == null) {
            LoggerHelper.e(TAG, " 用户信息获取失败 失败信息：context =  null");
            return null;
        }
        return getValueBySharedPreferences(context, Config.DB.USER_NAME,
                Config.DB.USER_KEY, User.class);
    }

    /**
     * 获取用户登录id
     *
     * @param context 上下文对象
     * @return 用户登录id
     */
    public static String getUserId(Context context) {
        if (context == null) {
            LoggerHelper.e(TAG, " 用户id获取失败 失败信息：context =  null");
            return "";
        }

        User user = getUserInfo(context);
        if (user == null) {
            LoggerHelper.e(TAG, " 用户id获取失败 失败信息：user =  null");
            return "";
        }
        return user.getUserId();
    }

    /**
     * 获取AppToken
     *
     * @param context 上下文对象
     * @return apptoken
     */
    public static String getAppToken(Context context) {
        if (context == null) {
            LoggerHelper.e(TAG, " AppToken获取失败 失败信息：context =  null");
            return "";
        }

        User user = getUserInfo(context);
        if (user == null) {
            LoggerHelper.e(TAG, " AppToken获取失败 失败信息：user =  null");
            return "";
        }
        return user.getApp_token();
    }


    /**
     * 判断用户是否已经登录
     *
     * @param context 上下文对象
     * @return true : 已经登录 false: 未登录
     */
    public static boolean checkUserIsLogin(Context context) {
        if (context == null) {
            return false;
        }
        IsLogin isLogin = getValueBySharedPreferences(context, Config.DB.IS_LOGIN_NAME,
                Config.DB.IS_LOGIN_KEY, IsLogin.class);
        return isLogin != null && isLogin.isLogin();
    }


    /**
     * 判断当前版本是否是最新版本
     *
     * @param context 上下文对象
     * @return true: 最新版本  false：不是最新版本
     */
    public static boolean checkIsNewVersion(Context context) {
        if (context == null) {
            return true;
        }
        IsNewVersion isNewVersion = getValueBySharedPreferences(context, Config.DB.IS_NEW_VERSION_NAME,
                Config.DB.IS_NEW_VERSION_KEY, IsNewVersion.class);
        return isNewVersion == null || isNewVersion.isNewVersion();
    }

}
