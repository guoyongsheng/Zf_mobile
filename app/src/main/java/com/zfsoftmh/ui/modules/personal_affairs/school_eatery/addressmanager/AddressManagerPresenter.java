package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/7/19.
 */

public class AddressManagerPresenter implements AddressManagerContract.Presenter {


    AddressManagerContract.View view;
    HttpManager httpManager;
    PersonalAffairsApi personalAffairsApi;
    CompositeDisposable compositeDisposable;

    public AddressManagerPresenter(AddressManagerContract.View view,PersonalAffairsApi personalAffairsApi,HttpManager httpManager){
        this.view=view;
        this.personalAffairsApi=personalAffairsApi;
        this.httpManager=httpManager;
        compositeDisposable=new CompositeDisposable();
        view.setPresenter(this);


    }

    @Override
    public void loadData(Map<String, String> params) {
        httpManager.request(personalAffairsApi.getUserAddress(params), compositeDisposable, view, new CallBackListener<ResponseListInfo<UserAddressInfo>>() {
            @Override
            public void onSuccess(ResponseListInfo<UserAddressInfo> data) {
              //  view.loadSuccess(data);
                view.loadData(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.loadFailure(errorMsg);
            }
        });

    }

    @Override
    public void cancelRequest() {

    }
}
