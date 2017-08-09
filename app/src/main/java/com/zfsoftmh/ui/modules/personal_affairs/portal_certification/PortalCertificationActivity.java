package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 门户认证界面
 */

public class PortalCertificationActivity extends BaseActivity {

    private FragmentManager manager;
    private PortalCertificationFragment fragment;
    private String id;
    private String url;

    @Inject
    PortalCertificationPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

        id = bundle.getString("id");
        url = bundle.getString("url");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.portal_certification);
        setDisplayHomeAsUpEnabled(true);

        fragment = (PortalCertificationFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = PortalCertificationFragment.newInstance(id, url);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {

        DaggerPortalCertificationComponent.builder()
                .appComponent(getAppComponent())
                .portalCertificationPresenterModule(new PortalCertificationPresenterModule(fragment))
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
