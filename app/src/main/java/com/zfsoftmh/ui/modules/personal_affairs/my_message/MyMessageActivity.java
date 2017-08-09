package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public class MyMessageActivity extends BaseActivity{
    private FragmentManager manager;
    private MyMessageFragment fragment;

    @Inject
    MyMessagePresenter presenter;

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
        setToolbarTitle(R.string.mine_message);
        setDisplayHomeAsUpEnabled(true);
        fragment = (MyMessageFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = MyMessageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerMyMessageComponent.builder()
                .appComponent(getAppComponent())
                .myMessagePresenterModule(new MyMessagePresenterModule(fragment))
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
