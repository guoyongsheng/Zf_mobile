package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 会议管理界面
 */

public class MeetingManagementActivity extends BaseActivity {

    private FragmentManager manager;
    private MeetingManagementFragment fragment;

    @Inject
    MeetingManagementPresenter presenter;

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
        setToolbarTitle(R.string.meeting_management);
        setDisplayHomeAsUpEnabled(true);

        fragment = (MeetingManagementFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = MeetingManagementFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerMeetingManagementComponent.builder()
                .appComponent(getAppComponent())
                .meetingManagementPresenterModule(new MeetingManagementPresenterModule(fragment))
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
