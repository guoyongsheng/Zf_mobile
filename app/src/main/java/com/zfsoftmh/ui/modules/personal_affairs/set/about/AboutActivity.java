package com.zfsoftmh.ui.modules.personal_affairs.set.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 关于界面
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        TextView tv_version_code = (TextView) findViewById(R.id.about_version_code);
        TextView tv_url = (TextView) findViewById(R.id.about_url_text);
        setToolbarTitle(R.string.about_us);
        setDisplayHomeAsUpEnabled(true);
        tv_version_code.setText(AppUtils.getAppVersionName(this));
        tv_url.setText(Config.URL.BASE_URL);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }
}
