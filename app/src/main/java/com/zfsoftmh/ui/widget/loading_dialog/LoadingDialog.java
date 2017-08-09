package com.zfsoftmh.ui.widget.loading_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;

/**
 * @author wangshimei
 * @date: 17/8/7
 * @Description: 加载框
 */

public class LoadingDialog extends Dialog {

    private Context context = null;
    private int anim = 0;
    private static LoadingDialog commProgressDialog = null;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme, int anim) {
        super(context, theme);
        this.anim = anim;
    }

    public static LoadingDialog createDialog(Context context, int anim) {
        commProgressDialog = new LoadingDialog(context, R.style.CommProgressDialog, anim);
        commProgressDialog.setContentView(R.layout.loading_dialog_view);
        commProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return commProgressDialog;
    }


    public void onWindowFocusChanged(boolean hasFocus) {

        if (commProgressDialog == null) {
            return;
        }

        ImageView imageView = (ImageView) commProgressDialog.findViewById(R.id.loadingIv);
        if (anim != 0) {
            imageView.setBackgroundResource(anim);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     * 设置标题
     *
     * @param strTitle
     * @return
     */
    public LoadingDialog setTitile(String strTitle) {
        return commProgressDialog;
    }

    /**
     * 设置提示内容
     *
     * @param strMessage
     * @return
     */
    public LoadingDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) commProgressDialog.findViewById(R.id.loadingTv);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return commProgressDialog;
    }

    /**
     * 屏蔽返回键
     **/
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//            return true;
//        return super.onKeyDown(keyCode, event);
//    }
}
