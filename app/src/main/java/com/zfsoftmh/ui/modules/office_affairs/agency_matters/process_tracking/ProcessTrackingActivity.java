package com.zfsoftmh.ui.modules.office_affairs.agency_matters.process_tracking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 流程跟踪界面
 */

public class ProcessTrackingActivity extends BaseActivity {

    private FragmentManager manager;
    private ProcessTrackingFragment fragment;

    @Inject
    ProcessTrackingPresenter presenter;

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
        setToolbarTitle(R.string.process_tracking);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ProcessTrackingFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ProcessTrackingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerProcessTrackingComponent.builder()
                .appComponent(getAppComponent())
                .processTrackingPresenterModule(new ProcessTrackingPresenterModule(fragment))
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
