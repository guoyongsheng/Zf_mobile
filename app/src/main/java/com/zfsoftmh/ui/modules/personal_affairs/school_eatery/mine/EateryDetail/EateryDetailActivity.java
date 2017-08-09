package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ljq
 * on 2017/7/25.
 */

public class EateryDetailActivity extends BaseActivity {

    FragmentManager manager;
    EateryDetailFragment fragment;
    private  String id;
    private EateryInfo info;

    @Inject
    EateryDetailPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        info=bundle.getParcelable("eatery");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("订餐");
        setDisplayHomeAsUpEnabled(true);
        fragment = (EateryDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = EateryDetailFragment.newInstance(info);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }


    }

    @Override
    protected void setUpInject() {
        DaggerEateryDetailComponent.builder()
                .appComponent(getAppComponent())
                .eateryDetailModule(new EateryDetailModule(fragment))
                .build()
                .inject(this);

    }

    @Override
    protected void initListener() {

    }
}
