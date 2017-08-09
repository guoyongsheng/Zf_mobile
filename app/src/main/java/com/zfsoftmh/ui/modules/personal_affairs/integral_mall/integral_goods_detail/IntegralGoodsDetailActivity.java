package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description: 积分商品详情
 */

public class IntegralGoodsDetailActivity extends BaseActivity{
    private FragmentManager manager;
    private IntegralGoodsDetailFragment fragment;
    private IntegralMallGoodsInfo bean;

    @Inject
    IntegralGoodsDetailPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        bean = bundle.getParcelable("IntegralMallFragment");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.integral_goods_detail_title);
        setDisplayHomeAsUpEnabled(true);

        fragment = (IntegralGoodsDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = IntegralGoodsDetailFragment.newInstance(bean);
            ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerIntegralGoodsDetailComponent.builder()
                .appComponent(getAppComponent())
                .integralGoodsDetailPresenterModule(new IntegralGoodsDetailPresenterModule(fragment))
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
