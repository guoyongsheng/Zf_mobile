package com.zfsoftmh.ui.modules.office_affairs.agency_matters.submit;

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
 * @Description: 提交待办事宜界面
 */

public class SubmitMattersActivity extends BaseActivity {

    private FragmentManager manager;
    private SubmitMattersFragment fragment;

    @Inject
    SubmitMattersPresenter presenter;

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
        setToolbarTitle(R.string.submit_matters);
        setDisplayHomeAsUpEnabled(true);

        fragment = (SubmitMattersFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SubmitMattersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerSubmitMattersComponent.builder()
                .appComponent(getAppComponent())
                .submitMattersPresenterModule(new SubmitMattersPresenterModule(fragment))
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
