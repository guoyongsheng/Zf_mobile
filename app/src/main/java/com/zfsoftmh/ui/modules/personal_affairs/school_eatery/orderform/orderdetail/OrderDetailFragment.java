package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li
 * on 2017/8/2.
 */

public class OrderDetailFragment extends BaseFragment<OrderDetailPresenter> implements OrderDetailContract.View {

    RecyclerView recyclerView;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_orderdetail;
    }

    @Override
    protected void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setAdapter(new OrderDetailAdapter(getContext(),));
        getData();
    }



    private void  getData(){
        List<FoodInfo> foodInfos=new ArrayList<>();
        for(int i=0;i<88;i++){
            FoodInfo foodInfo=new FoodInfo();
            foodInfo.setCount(i);
            foodInfo.setPrice(""+i*2);
            foodInfo.setFoodName("我是食物"+i+"号");
            foodInfos.add(foodInfo);
        }


        recyclerView.setAdapter(new OrderDetailAdapter(getContext(),foodInfos));

    }

    @Override
    protected void initListener() {

    }
}
