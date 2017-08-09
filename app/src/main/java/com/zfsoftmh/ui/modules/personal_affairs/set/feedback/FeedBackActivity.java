package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 意见反馈
 */

public class FeedBackActivity extends BaseActivity {

    @Inject
    FeedBackPresenter feedBackPresenter;
    private FeedBackFragment fragment;
    private FragmentManager manager;

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
        setToolbarTitle(R.string.feed_back);
        setDisplayHomeAsUpEnabled(true);

        fragment = (FeedBackFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = FeedBackFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerFeedBackComponent.builder()
                .appComponent(getAppComponent())
                .feedBackPresenterModule(new FeedBackPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    protected void initListener() {

    }
}
