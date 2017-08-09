package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.entity.OrderForminfo;
import com.zfsoftmh.ui.base.BaseListFragment;


/**
 * Created by ljq
 * on 2017/7/19.
 */

public class OrderFormFragment extends BaseListFragment<OrderFormPresenter,OrderForminfo> implements OrderFormAdapter.OnMyItemViewClickListener ,OrderFormContract.View{

    OrderFormAdapter adapter;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static OrderFormFragment newInstance(){
        return  new OrderFormFragment();
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected RecyclerArrayAdapter<OrderForminfo> getAdapter() {
        adapter=new OrderFormAdapter(getContext());
        return adapter;
    }

    @Override
    public void setOnMyItemViewClick(int pos, View v, int Type) {
        if(Type==OrderFormAdapter.TYPE_ONEMORE){
            Toast.makeText(getContext(),"点击了再来一单",Toast.LENGTH_SHORT).show();
        }else if(Type==OrderFormAdapter.TYPE_DETAIL){
            Toast.makeText(getContext(),"点击了详情",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"点击了店铺",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadData() {
        getOrderFormList(start_page);
    }

    private void getOrderFormList(int start){
        presenter.loadData(String.valueOf(start), String.valueOf(10));
    }




    @Override
    public void onItemClick(int position) {
    }
}
