package com.zfsoftmh.ui.modules.personal_affairs.set;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 设置界面
 */

public class SettingActivity extends BaseActivity {

    private SettingFragment fragment;
    private FragmentManager manager;

    @Inject
    SettingPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.set);
        setDisplayHomeAsUpEnabled(true);

        fragment = (SettingFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SettingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerSettingComponent.builder()
                .appComponent(getAppComponent())
                .settingPresenterModule(new SettingPresenterModule(fragment))
                .build()
                .inject(this);
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
