package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description: 积分排名
 */

public class IntegralRankingActivity extends BaseActivity {
    private FragmentManager manager;
    private IntegralRankingFragment fragment;

    @Inject
    IntegralRankingPresenter presenter;

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
        setToolbarTitle(R.string.points_ranking);
        setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundResource(R.mipmap.bg2);
        fragment = (IntegralRankingFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = IntegralRankingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerIntegralRankingComponent.builder()
                .appComponent(getAppComponent())
                .integralRankingPresenterModule(new IntegralRankingPresenterModule(fragment))
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
    protected void setStatusBar() {
        super.setStatusBar();
        immersionBar.transparentStatusBar().init();
    }
}
