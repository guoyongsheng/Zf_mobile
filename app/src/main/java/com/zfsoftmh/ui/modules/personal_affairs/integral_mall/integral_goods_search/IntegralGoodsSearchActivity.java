package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description: 积分商城商品搜索页
 */

public class IntegralGoodsSearchActivity extends BaseActivity {
    private FragmentManager manager;
    private IntegralGoodsSearchFragment fragment;

    @Inject
    IntegralGoodsSearchPresenter presenter;

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
        setToolbarTitle(R.string.search);
        setDisplayHomeAsUpEnabled(true);
        fragment = (IntegralGoodsSearchFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = IntegralGoodsSearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
//        DaggerIntegralGoodsSearchComponent.builder()
//                .appComponent(getAppComponent())
//                .integralGoodsSearchPresenterModule(new IntegralGoodsSearchPresenterModule(fragment))
//                .build()
//                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}
