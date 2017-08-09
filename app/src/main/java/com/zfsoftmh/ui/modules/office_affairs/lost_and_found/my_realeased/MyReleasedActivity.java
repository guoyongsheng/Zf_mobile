package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 我的发布界面
 */

public class MyReleasedActivity extends BaseActivity {

    private FragmentManager manager;
    private MyReleasedFragment fragment;

    @Inject
    MyReleasedPresenter presenter;

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
        setToolbarTitle(R.string.my_released);
        setDisplayHomeAsUpEnabled(true);

        fragment = (MyReleasedFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = MyReleasedFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerMyReleasedComponent.builder()
                .appComponent(getAppComponent())
                .myReleasedPresenterModule(new MyReleasedPresenterModule(fragment))
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
