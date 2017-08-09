package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseDialogFragment;

/**
 * @author wesley
 * @date: 2017/4/19
 * @Description: 对话框
 */

public class SubscriptionCenterDialogFragment extends BaseDialogFragment implements
        DialogInterface.OnClickListener {

    private OnClickListener listener;

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected Dialog createDialog() {
        return new AlertDialog.Builder(context)
                .setMessage(getResources().getString(R.string.is_save_edit))
                .setPositiveButton(R.string.Ok, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
        /*
         * setPositiveButton
         */
        case -1:
            if (listener != null) {
                listener.onOkClick();
            }
            break;

        /*
         * setNegativeButton
         */
        case -2:
            if (listener != null) {
                listener.onCancelClick();
            }
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


    void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public static SubscriptionCenterDialogFragment newInstance() {
        return new SubscriptionCenterDialogFragment();
    }

    /**
     * 自定义回调接口
     */
    interface OnClickListener {

        void onOkClick();

        void onCancelClick();
    }
}
