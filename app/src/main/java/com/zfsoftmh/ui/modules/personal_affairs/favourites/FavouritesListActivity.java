package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description: 我的收藏列表
 */

public class FavouritesListActivity extends BaseActivity{
    private FragmentManager manager;
    private FavouritesListFragment fragment;

    @Inject
    FavouritesListPresenter presenter;

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
        setToolbarTitle(R.string.my_collection);
        setDisplayHomeAsUpEnabled(true);

        fragment = (FavouritesListFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = FavouritesListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }

    }

    @Override
    protected void setUpInject() {
        DaggerFavouritesListComponent.builder()
                .appComponent(getAppComponent())
                .favouritesListPresenterModule(new FavouritesListPresenterModule(fragment))
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
