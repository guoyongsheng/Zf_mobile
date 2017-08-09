package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

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
 * @Description: 读者信息界面
 */

public class ReaderInformationActivity extends BaseActivity {

    private FragmentManager manager;
    private ReaderInformationFragment fragment;

    @Inject
    ReaderInformationPresenter presenter;

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
        setToolbarTitle(R.string.reader_information);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ReaderInformationFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ReaderInformationFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerReaderInformationComponent.builder()
                .appComponent(getAppComponent())
                .readerInformationPresenterModule(new ReaderInformationPresenterModule(fragment))
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
