package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit.OrderSubmitFragment;

import javax.inject.Inject;

/**
 * Created by li
 * on 2017/8/2.
 */

public class OrderDetailActivity extends BaseActivity {

    FragmentManager fragmentManager;
    OrderDetailFragment fragment;


    @Inject
    OrderDetailPresenter presenter;

    @Override
    protected void initVariables() {
        fragmentManager=getSupportFragmentManager();
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

        fragment= (OrderDetailFragment) fragmentManager.findFragmentById(R.id.common_content);
        if(fragment==null){
            fragment=new OrderDetailFragment();
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerOrderDetailComponent.builder()
                .appComponent(getAppComponent())
                .orderDetailModule(new OrderDetailModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }
}
