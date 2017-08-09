package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 订阅中心
 */

public class SubscriptionCenterActivity extends BaseActivity {

    private static final String TAG = "SubscriptionCenterActivity";
    private FragmentManager manager;
    private SubscriptionCenterFragment fragment;
    private ArrayList<AppCenterItemInfo> list;

    @Inject
    SubscriptionCenterPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.subscription_center);
        setDisplayHomeAsUpEnabled(true);

        fragment = (SubscriptionCenterFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SubscriptionCenterFragment.newInstance(list);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {

        DaggerSubscriptionCenterComponent.builder()
                .appComponent(getAppComponent())
                .subscriptionCenterPresenterModule(new SubscriptionCenterPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPress();
    }

    @Override
    public void onBackPressed() {
        onBackPress();
    }

    //处理返回键
    private void onBackPress() {

        if (fragment.checkIsEditMode()) {
            fragment.setIsNotEditMode();
            return;
        }

        if (!fragment.checkHasSelectedApp()) {
            showToastMsgShort(getResources().getString(R.string.please_select_app));
            return;
        }
        fragment.saveSelectService();
    }
}
