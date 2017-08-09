package com.zfsoftmh.ui.modules.personal_affairs.email;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 我的邮件界面
 */

public class EmailActivity extends BaseActivity {

    private AppCenterItemInfo itemInfo;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        itemInfo = bundle.getParcelable("info");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(itemInfo.getName());
        setDisplayHomeAsUpEnabled(true);
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
