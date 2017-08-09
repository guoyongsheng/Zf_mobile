package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

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
 * @Description: 退回待办事宜的界面
 */

public class WithDrawMattersActivity extends BaseActivity {

    private FragmentManager manager;
    private WithDrawMattersFragment fragment;

    @Inject
    WithDrawMattersPresenter presenter;

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
        setToolbarTitle(R.string.with_draw_matters);
        setDisplayHomeAsUpEnabled(true);

        fragment = (WithDrawMattersFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = WithDrawMattersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerWithDrawMattersComponent.builder()
                .appComponent(getAppComponent())
                .withDrawMattersPresenterModule(new WithDrawMattersPresenterModule(fragment))
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
