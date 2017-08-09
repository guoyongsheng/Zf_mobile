package com.zfsoftmh.ui.modules.personal_affairs.set.common_setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.fontsize.SetFontSizeActivity;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 通用设置
 */

public class CommonSettingActivity extends BaseActivity implements View.OnClickListener {

    private SwitchCompat switch_receive_msg; //接收消息
    private SwitchCompat switch_day_light; //夜间模式和夜间模式
    private RelativeLayout rl_font_size; //字体大小
    private RelativeLayout rl_app_theme; //应用主题

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_common_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.common_set);
        setDisplayHomeAsUpEnabled(true);

        switch_receive_msg = (SwitchCompat) findViewById(R.id.common_setting_receive_msg);
        switch_day_light = (SwitchCompat) findViewById(R.id.common_setting_day_light);
        rl_font_size = (RelativeLayout) findViewById(R.id.common_setting_rl_font_size);
        rl_app_theme = (RelativeLayout) findViewById(R.id.common_setting_app_theme);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        rl_font_size.setOnClickListener(this);
        rl_app_theme.setOnClickListener(this);

        switch_day_light.setOnClickListener(this);
        switch_receive_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 字体大小
         */
        case R.id.common_setting_rl_font_size:
            startActivity(SetFontSizeActivity.class);
            break;

        /*
         * 应用主题
         */
        case R.id.common_setting_app_theme:
            break;


        /*
         * 夜间模式和日间模式
         */
        case R.id.common_setting_day_light:
            //switchDayNightMode();
            break;

        /*
         * 接收消息
         */
        case R.id.common_setting_receive_msg:
            break;

        default:
            break;
        }
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }


    //切换夜间模式和日间模式
    private void switchDayNightMode() {
        boolean isChecked = switch_day_light.isChecked();
        if (isChecked) {
            switch_day_light.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);//切换日间模式
            recreate();
        } else {
            switch_day_light.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//切换夜间模式
            recreate();
        }
    }
}
