package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 办公通讯录界面
 */

public class OfficeContactsActivity extends BaseActivity {

    private OfficeContactsFragment fragment;
    private FragmentManager manager;

    @Inject
    OfficeContactsPresenter presenter;

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
        setToolbarTitle(R.string.office_contacts);
        setDisplayHomeAsUpEnabled(true);

        fragment = (OfficeContactsFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = OfficeContactsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerOfficeContactsComponent.builder()
                .appComponent(getAppComponent())
                .officeContactsPresenterModule(new OfficeContactsPresenterModule(fragment))
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
