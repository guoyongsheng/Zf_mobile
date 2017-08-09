package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by li
 * on 2017/8/2.
 */

public class OrderDetailPresenter implements OrderDetailContract.Presenter {

    OrderDetailContract.View view;
    PersonalAffairsApi personalAffairsApi;
    HttpManager httpManager;
    CompositeDisposable compositeDisposable;

    public OrderDetailPresenter(OrderDetailContract.View view, PersonalAffairsApi personalAffairsApi,
                                HttpManager httpManager){
        this.view=view;
        this.personalAffairsApi=personalAffairsApi;
        this.httpManager=httpManager;
        compositeDisposable=new CompositeDisposable();
        view.setPresenter(this);


    }







    @Override
    public void cancelRequest() {

    }
}
