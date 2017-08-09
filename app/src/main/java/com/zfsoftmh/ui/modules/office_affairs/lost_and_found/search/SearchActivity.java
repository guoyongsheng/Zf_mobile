package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 失物招领的搜索界面
 */

public class SearchActivity extends BaseActivity {

    private FragmentManager manager;
    private SearchFragment fragment;
    private int currentItem; // 0: 未招领 1: 已招领

    @Inject
    SearchPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        currentItem = bundle.getInt("currentItem");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.search);
        setDisplayHomeAsUpEnabled(true);

        fragment = (SearchFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SearchFragment.newInstance(currentItem);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerSearchComponent.builder()
                .appComponent(getAppComponent())
                .searchPresenterModule(new SearchPresenterModule(fragment))
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
