package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import android.content.Context;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by li
 * on 2017/7/31.
 */

public class OrderSubmitPresenter implements OrderSubmitContract.Presenter{
    private OrderSubmitContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi personalAffairsApi;
    private CompositeDisposable compositeDisposable;

    public OrderSubmitPresenter(OrderSubmitContract.View view,HttpManager httpManager,PersonalAffairsApi personalAffairsApi){
        this.view=view;
        this.httpManager=httpManager;
        this.personalAffairsApi=personalAffairsApi;
        compositeDisposable=new CompositeDisposable();
        view.setPresenter(this);

    }






    @Override
    public void cancelRequest() {

    }

    @Override
    public void SubmitOrder(String data) {
        httpManager.request(personalAffairsApi.submitOrder(data), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.SubmitOrderSucess(data);
            }

            @Override
            public void onError(String errorMsg) {
             view.SubmitOrderErr(errorMsg);
            }
        });



    }
}
