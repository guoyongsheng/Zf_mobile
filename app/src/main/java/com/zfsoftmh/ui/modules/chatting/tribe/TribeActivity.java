package com.zfsoftmh.ui.modules.chatting.tribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by sy
 * on 2017/5/12.
 * <p>群组</p>
 */
public class TribeActivity extends BaseActivity{

    private FragmentManager manager;
    private TribeFragment fragment;

    @Inject
    TribePresenter presenter;

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
        setToolbarTitle("群组");
        setDisplayHomeAsUpEnabled(true);
        fragment = (TribeFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = TribeFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
    }

    @Override
    protected void setUpInject() {
        DaggerTribeComponent.builder()
                .appComponent(getAppComponent())
                .tribePresenterModule(new TribePresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initListener() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
