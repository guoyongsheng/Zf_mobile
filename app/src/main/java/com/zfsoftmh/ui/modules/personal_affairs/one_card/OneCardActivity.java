package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 一卡通
 */

public class OneCardActivity extends BaseActivity {

    private OneCardFragment fragment;
    private FragmentManager manager;

    @Inject
    OneCardPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_one_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.one_card);
        setDisplayHomeAsUpEnabled(true);

        fragment = (OneCardFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = OneCardFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerOneCardComponent.builder()
                .appComponent(getAppComponent())
                .oneCardPresenterModule(new OneCardPresenterModule(fragment))
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
