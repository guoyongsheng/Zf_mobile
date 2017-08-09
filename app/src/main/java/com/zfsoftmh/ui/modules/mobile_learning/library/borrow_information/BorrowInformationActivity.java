package com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 借阅信息界面
 */

public class BorrowInformationActivity extends BaseActivity {

    private BorrowInformationFragment fragment;
    private FragmentManager manager;

    @Inject
    BorrowInformationPresenter presenter;

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
        setToolbarTitle(R.string.borrow_information);
        setDisplayHomeAsUpEnabled(true);

        fragment = (BorrowInformationFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = BorrowInformationFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerBorrowInformationComponent.builder()
                .appComponent(getAppComponent())
                .borrowInformationPresenterModule(new BorrowInformationPresenterModule(fragment))
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
