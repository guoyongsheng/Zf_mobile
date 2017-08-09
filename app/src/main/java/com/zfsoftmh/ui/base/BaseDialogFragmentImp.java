package com.zfsoftmh.ui.base;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;

/**
 * @author wesley
 * @date: 2017-8-8
 * @Description: 对话框实现
 */

public class BaseDialogFragmentImp extends BaseDialogFragment {

    private String msg;
    private AnimationDrawable animationDrawable;

    @Override
    protected void handleBundle(Bundle bundle) {
        msg = bundle.getString("msg");
    }

    @Override
    protected Dialog createDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);
        TextView tv_value = (TextView) view.findViewById(R.id.loadingTv);
        ImageView iv_anim = (ImageView) view.findViewById(R.id.loadingIv);
        animationDrawable = (AnimationDrawable) iv_anim.getBackground();
        tv_value.setText(msg);
        Dialog dialog = new Dialog(context, R.style.CommProgressDialog);
        dialog.setContentView(view);
        animationDrawable.start();
        return dialog;
    }


    public static BaseDialogFragmentImp newInstance(String msg) {
        BaseDialogFragmentImp fragmentImp = new BaseDialogFragmentImp();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        fragmentImp.setArguments(bundle);
        return fragmentImp;
    }

    private void stopAnimation() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
            animationDrawable = null;
        }
    }

    @Override
    public void dismiss() {
        stopAnimation();
        super.dismiss();
    }
}
