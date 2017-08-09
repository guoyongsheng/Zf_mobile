package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: 日程管理界面
 */

public class ScheduleManagementActivity extends BaseActivity implements
        ScheduleManagementFragment.OnTitleChangeListener {

    private ScheduleManagementFragment fragment;
    private FragmentManager manager;

    @Inject
    ScheduleManagementPresenter presenter;

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

        fragment = (ScheduleManagementFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ScheduleManagementFragment.newInstance();
            fragment.setOnTitleChangeListener(this);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerScheduleManagementComponent.builder()
                .appComponent(getAppComponent())
                .scheduleManagementPresenterModule(new ScheduleManagementPresenterModule(fragment))
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

    @Override
    public void onTitleChanged(String title) {
        setToolbarTitle(title);
        setDisplayHomeAsUpEnabled(true);
    }
}
