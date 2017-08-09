package com.zfsoftmh.ui.modules.common.home.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseDialogFragment;


/**
 * @author wesley
 * @date: 2017-7-4
 * @Description: 对话框
 */

public class HomeItemDialogFragment extends BaseDialogFragment implements DialogInterface.OnClickListener {

    private int type; //类型
    private int position; //在列表中的位置
    private OnItemClickListener listener;

    @Override
    protected void handleBundle(Bundle bundle) {
        type = bundle.getInt("type");
        position = bundle.getInt("position");
    }

    @Override
    protected Dialog createDialog() {
        return new AlertDialog.Builder(context)
                .setItems(R.array.home_item, this)
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static HomeItemDialogFragment newInstance(int type, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("position", position);
        HomeItemDialogFragment fragment = new HomeItemDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (listener == null) {
            return;
        }
        switch (which) {
        /*
         * 忽略此动态
         */
        case 0:
            listener.onItemIgnoreClick(type, position);
            break;

        /*
         * 不再接收此动态
         */
        case 1:
            listener.onItemNoLongerClick(type, position);
            break;


        default:
            break;
        }
    }

    interface OnItemClickListener {

        void onItemIgnoreClick(int type, int position);

        void onItemNoLongerClick(int type, int position);
    }
}
