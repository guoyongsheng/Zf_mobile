package com.zfsoftmh.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author wesley
 * @date: 2017/3/17
 * @Description: 软键盘的工具类
 */

public class KeyboardUtils {

    private KeyboardUtils() {

    }

    /**
     * 隐藏软键盘
     *
     * @param activity Activity的对象
     */
    public static void hideSoftInput(Activity activity) {

        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 显示软键盘
     *
     * @param edit 输入框
     */
    public static void showSoftInput(EditText edit, Context context) {
        if (edit == null || context == null) {
            return;
        }
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.showSoftInput(edit, 0);
    }
}
