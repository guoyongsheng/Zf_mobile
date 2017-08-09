package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 充值明细界面
 */

public class RechargeDetailsActivity extends BaseActivity {

    private RechargeDetailsFragment fragment;
    private FragmentManager manager;
    private String oneCardId; //一卡通id

    @Inject
    RechargeDetailsPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        oneCardId = bundle.getString("id");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.recharge_details);
        setDisplayHomeAsUpEnabled(true);

        fragment = (RechargeDetailsFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = RechargeDetailsFragment.newInstance(oneCardId);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerRechargeDetailsComponent.builder()
                .appComponent(getAppComponent())
                .rechargeDetailsPresenterModule(new RechargeDetailsPresenterModule(fragment))
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
