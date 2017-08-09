package com.zfsoftmh.ui.modules.mobile_learning.library.arrears_information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 欠费信息界面
 */

public class ArrearsInformationActivity extends BaseActivity {

    private ArrearsInformationFragment fragment;
    private FragmentManager manager;

    @Inject
    ArrearsInformationPresenter presenter;

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
        setToolbarTitle(R.string.arrears);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ArrearsInformationFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ArrearsInformationFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerArrearsInformationComponent.builder()
                .appComponent(getAppComponent())
                .arrearsInformationPresenterModule(new ArrearsInformationPresenterModule(fragment))
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
