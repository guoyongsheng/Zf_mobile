package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

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
 * @Description: 卡片充值界面
 */

public class CardRechargeActivity extends BaseActivity {

    private CardRechargeFragment fragment;
    private FragmentManager manager;

    @Inject
    CardRechargePresenter presenter;

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
        setToolbarTitle(R.string.card_recharge);
        setDisplayHomeAsUpEnabled(true);

        fragment = (CardRechargeFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = CardRechargeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerCardRechargeComponent.builder()
                .appComponent(getAppComponent())
                .cardRechargePresenterModule(new CardRechargePresenterModule(fragment))
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
