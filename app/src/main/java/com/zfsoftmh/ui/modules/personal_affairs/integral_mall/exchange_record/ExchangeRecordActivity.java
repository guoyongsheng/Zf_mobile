package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 积分兑换记录
 */

public class ExchangeRecordActivity extends BaseActivity {

    private FragmentManager manager;
    private ExchangeRecordFragment fragment;

    @Inject
    ExchangeRecordPresenter presenter;

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
        setToolbarTitle(R.string.exchange_record_title);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ExchangeRecordFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ExchangeRecordFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
//        DaggerExchangeRecordComponent.builder()
//                .appComponent(getAppComponent())
//                .exchangeRecordPresenterModule(new ExchangeRecordPresenterModule(fragment))
//                .build()
//                .inject(this);
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
