package com.zfsoftmh.ui.modules.common.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * 判断用户是否是第一次登录
 * (1) 是第一次登录 打点统计 然后跳转到GuideActivity
 * (2) 不是第一次登录 则跳转到主页
 *
 * @author wesley
 * @date: 2017/3/14
 * @Description: 程序的入口
 */
public class SplashActivity extends BaseActivity {

    private SplashFragment fragment;
    private FragmentManager manager;

    @Inject
    SplashPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_content;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        fragment = (SplashFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerSplashComponent.builder()
                .appComponent(getAppComponent())
                .splashPresenterModule(new SplashPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean immersionEnabled() {
        return false;
    }
}
