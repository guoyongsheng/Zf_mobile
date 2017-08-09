package com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details;

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
 * @Description: 消费明细界面
 */

public class ConsumerDetailsActivity extends BaseActivity {

    private ConsumerDetailsFragment fragment;
    private FragmentManager manager;
    private String oneCardId; //一卡通id

    @Inject
    ConsumerDetailsPresenter presenter;

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
        setToolbarTitle(R.string.consumer_details);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ConsumerDetailsFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ConsumerDetailsFragment.newInstance(oneCardId);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerConsumerDetailsComponent.builder()
                .appComponent(getAppComponent())
                .consumerDetailsPresenterModule(new ConsumerDetailsPresenterModule(fragment))
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
