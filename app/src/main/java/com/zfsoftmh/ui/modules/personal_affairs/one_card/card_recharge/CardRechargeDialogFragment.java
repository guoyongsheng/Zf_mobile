package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.KeyboardUtils;
import com.zfsoftmh.ui.base.BaseDialogFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017/4/14
 * @Description: 输入充值金额的对话框
 */

public class CardRechargeDialogFragment extends BaseDialogFragment implements
        DialogInterface.OnClickListener, TextWatcher {

    private OnOkClickListener listener;
    private EditText et_recharge_amount;

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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recharge_amount, null);
        et_recharge_amount = (EditText) view.findViewById(R.id.recharge_amount);
        et_recharge_amount.addTextChangedListener(this);
        showSoftPan();
        return new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(R.string.Ok, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }

    public static CardRechargeDialogFragment newInstance() {
        return new CardRechargeDialogFragment();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
        /*
         *  確定
         */
        case -1:
            if (listener != null) {
                listener.onOkClick(getRechargeAmount());
            }
            break;


        default:
            break;
        }
    }

    public void setOnOkClickListener(OnOkClickListener listener) {
        this.listener = listener;
    }


    private String getRechargeAmount() {
        return et_recharge_amount.getText().toString();
    }


    private void showSoftPan() {
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        KeyboardUtils.showSoftInput(et_recharge_amount, context);
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null) {
            return;
        }

        String value = s.toString();
        if (value.contains(".")) {
            if (value.length() - 1 - value.indexOf(".") > 2) {
                s = value.subSequence(0,
                        value.indexOf(".") + 3);
                et_recharge_amount.setText(s);
                et_recharge_amount.setSelection(s.length());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 自定义回调接口
     */
    interface OnOkClickListener {

        void onOkClick(String rechargeAmount);
    }
}
