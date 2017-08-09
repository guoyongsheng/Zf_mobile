package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.OrderForminfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ljq
 * on 2017/7/19.
 */

public class OrderFormPresenter implements OrderFormContract.Presenter {

    OrderFormContract.View view;
    PersonalAffairsApi personalAffairsApi;
    HttpManager httpManager;
    CompositeDisposable compositeDisposable;



    public OrderFormPresenter(OrderFormContract.View view,PersonalAffairsApi personalAffairsApi,HttpManager httpManager){
        this.view=view;
        this.personalAffairsApi=personalAffairsApi;
        this.httpManager=httpManager;
        compositeDisposable=new CompositeDisposable();
        view.setPresenter(this);
    }



    @Override
    public void cancelRequest() {

    }

    @Override
    public void loadData(String start,String size) {
         httpManager.request(personalAffairsApi.getOrderFormList(start, size), compositeDisposable, view, new CallBackListener<ResponseListInfo<OrderForminfo>>() {
             @Override
             public void onSuccess(ResponseListInfo<OrderForminfo> data) {
                 view.loadSuccess(data);
             }

             @Override
             public void onError(String errorMsg) {
                view.loadFailure(errorMsg);
             }
         });
    }
}
