package com.zfsoftmh.ui.modules.personal_affairs.set;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.ui.base.BaseDialogFragment;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 版本跟新的对话框
 */

public class SettingDialogFragment extends BaseDialogFragment implements DialogInterface.OnClickListener {

    private int updateType; // 跟新类型
    private VersionInfo versionInfo; //版本信息
    private OnUpdateClickListener listener; //回调接口

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        updateType = bundle.getInt("updateType");
        versionInfo = bundle.getParcelable("versionInfo");
    }

    @Override
    protected Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog;
        if (updateType == 2) {
            dialog = builder.setTitle(R.string.new_version)
                    .setMessage(versionInfo.getPrompt())
                    .setPositiveButton(R.string.update, this)
                    .setNegativeButton(R.string.cancel, this)
                    .setNeutralButton(R.string.update_later, this)
                    .create();
        } else {
            dialog = builder.setTitle(R.string.new_version)
                    .setMessage(versionInfo.getPrompt())
                    .setPositiveButton(R.string.update, this)
                    .create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        return dialog;

    }

    /**
     * 实例化Fragment对象
     *
     * @param versionInfo 版本信息
     * @param updateType  跟新类型 1: 强制跟新 2：非强制跟新
     * @return Fragment对象
     */
    public static SettingDialogFragment newInstance(VersionInfo versionInfo, int updateType) {
        SettingDialogFragment fragment = new SettingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("versionInfo", versionInfo);
        bundle.putInt("updateType", updateType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 设置监听
     *
     * @param listener 会调接口
     */
    public void setOnUpdateClickListener(OnUpdateClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogs, int which) {

        switch (which) {
        /*
         * setPositiveButton
         */
        case -1:
            if (listener != null) {
                listener.onUpdateClick(versionInfo.getUrl());
            }
            break;

        /*
         * setNegativeButton
         */
        case -2:
            break;

        /*
         * setNeutralButton
         */
        case -3:
            break;

        default:
            break;
        }
    }

    interface OnUpdateClickListener {

        /**
         * 跟新
         *
         * @param url 下载地址
         */
        void onUpdateClick(String url);
    }
}
