package com.zfsoftmh.ui.modules.personal_affairs.set.fontsize;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wangshimei
 * @date: 17/8/2
 * @Description: 设置字体大小
 */

public class SetFontSizeActivity extends BaseActivity implements FontSliderBar.OnSliderBarChangeListener {

    private TextView content_tv;
    private float fontSize = 1;
    private FontSliderBar slider_bar;
    private int mCurrentIndex = 0;
    private int reset = 0; // 是否改变字体

    @Override
    protected void initVariables() {
        mCurrentIndex = SharedPreferenceUtils.readInt(this, "mCurrentIndex", "mCurrentIndex");
        reset = SharedPreferenceUtils.readInt(this, "mCurrentIndex", "mCurrentIndex");
        fontSize = SharedPreferenceUtils.readFloat(SetFontSizeActivity.this, "fontSize", "fontSize");
        getResources();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_font_size;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.font_size);
        setDisplayHomeAsUpEnabled(true);
        content_tv = (TextView) findViewById(R.id.content_tv);
        setContentTextSize(fontSize);
        slider_bar = (FontSliderBar) findViewById(R.id.slider_bar);
        slider_bar.setTickCount(5).setTickHeight(30).setBarColor(Color.GRAY)
                .setTextColor(Color.GRAY).setTextPadding(20).setTextSize(30)
                .setThumbRadius(30).setThumbColorNormal(Color.CYAN).setThumbColorPressed(Color.GREEN)
                .withAnimation(false);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        slider_bar.setOnSliderBarChangeListener(this).setThumbIndex(mCurrentIndex).apply();
    }

    // 退出页面操作
    private void showResetDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("修改字体后,应用将重启!")
                .setCancelable(false) //表示点击dialog其它部分不能取消(除了“取消”，“确定”按钮)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 默认字体大小
                        SharedPreferenceUtils.write(SetFontSizeActivity.this, "fontSize", "fontSize",fontSize);
                        // 默认字体坐标
                        SharedPreferenceUtils.write(SetFontSizeActivity.this, "mCurrentIndex", "mCurrentIndex", mCurrentIndex);
                        getResources();
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();

    }

    /**
     * 设置默认字体大小
     *
     * @param fontSize
     */
    private void setContentTextSize(float fontSize) {
        String size = String.valueOf(fontSize);
        if (size.equals("0.7")) {
            content_tv.setTextSize(12);
        } else if (size.equals("0.85")) {
            content_tv.setTextSize(14);
        } else if (size.equals("1")) {
            content_tv.setTextSize(16);
        } else if (size.equals("1.15")) {
            content_tv.setTextSize(18);
        } else if (size.equals("1.30")) {
            content_tv.setTextSize(20);
        }
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        if (reset == mCurrentIndex) {
            finish();
        } else {
            showResetDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (reset == mCurrentIndex) {
                finish();
            } else {
                showResetDialog();
            }
        }
        return false;
    }

    @Override
    public void onIndexChanged(FontSliderBar rangeBar, int index) {
        switch (index) {
            case 0:
                content_tv.setTextSize(12);
                fontSize = (float) 0.70;
                break;
            case 1:
                content_tv.setTextSize(14);
                fontSize = (float) 0.85;
                break;
            case 2:
                content_tv.setTextSize(16);
                fontSize = (float) 1.00;
                break;
            case 3:
                content_tv.setTextSize(18);
                fontSize = (float) 1.15;
                break;
            case 4:
                content_tv.setTextSize(20);
                fontSize = (float) 1.30;
                break;
        }
        mCurrentIndex = index;
    }

    @Override
    public Resources getResources() {
        //获取到resources对象
        Resources res = super.getResources();
        //修改configuration的fontScale属性
        res.getConfiguration().fontScale = fontSize;
        //将修改后的值更新到metrics.scaledDensity属性上
        res.updateConfiguration(null, null);
        return res;
    }

}
