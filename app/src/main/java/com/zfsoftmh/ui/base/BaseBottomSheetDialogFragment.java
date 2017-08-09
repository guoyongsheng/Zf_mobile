package com.zfsoftmh.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;

/**
 * @author wesley
 * @date: 2017/3/27
 * @Description: 基类的对话框---从底部弹出
 */

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createBottomSheetDialog(savedInstanceState);
    }


    /**
     * 创建对话框
     *
     * @param savedInstanceState Bundle实例
     * @return Dialog 对象
     */
    protected abstract Dialog createBottomSheetDialog(Bundle savedInstanceState);
}
