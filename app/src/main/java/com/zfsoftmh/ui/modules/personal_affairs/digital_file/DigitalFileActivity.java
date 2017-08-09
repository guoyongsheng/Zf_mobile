package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 数字档案界面
 */

public class DigitalFileActivity extends BaseActivity {

    private FragmentManager manager;
    private DigitalFileFragment fragment;

    @Inject
    DigitalFilePresenter presenter;

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
        setToolbarTitle(R.string.digital_file);
        setDisplayHomeAsUpEnabled(true);

        fragment = (DigitalFileFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = DigitalFileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerDigitalFileComponent.builder()
                .appComponent(getAppComponent())
                .digitalFilePresenterModule(new DigitalFilePresenterModule(fragment))
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
