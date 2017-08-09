package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by li
 * on 2017/7/31.
 */

public class OrderSubmitActivity extends BaseActivity{

    FragmentManager fragmentManager;
    OrderSubmitFragment fragment;
    EateryInfo info;
    List<FoodInfo> foodlist;
    String totalPrice;


 @Inject
 OrderSubmitPresenter presenter;

    @Override
    protected void initVariables() {
        fragmentManager=getSupportFragmentManager();

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        foodlist= (List<FoodInfo>) bundle.getSerializable("order");
        info=bundle.getParcelable("eateryinfo");
        totalPrice=bundle.getString("totalprice");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("结算订单");
        setDisplayHomeAsUpEnabled(true);
        fragment= (OrderSubmitFragment) fragmentManager.findFragmentById(R.id.common_content);
        if(fragment==null){
           // fragment=new OrderSubmitFragment();
           fragment=OrderSubmitFragment.newInstance(info,foodlist,totalPrice);
            ActivityUtils.addFragmentToActivity(fragmentManager,fragment,R.id.common_content);
        }


    }

    @Override
    protected void setUpInject() {
        DaggerOrderSubmitComponent.builder()
                .appComponent(getAppComponent())
                .orderSubmitModule(new OrderSubmitModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }
}
