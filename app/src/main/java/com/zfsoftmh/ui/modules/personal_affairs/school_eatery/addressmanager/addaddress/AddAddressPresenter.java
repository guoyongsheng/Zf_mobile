package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class AddAddressPresenter implements AddAddressContract.Presenter {
    AddAddressContract.View view;
    HttpManager httpManager;
    PersonalAffairsApi personalAffairsApi;
    CompositeDisposable compositeDisposable;

    public AddAddressPresenter(AddAddressContract.View view, HttpManager httpManager, PersonalAffairsApi personalAffairsApi) {
        this.view = view;
        this.httpManager = httpManager;
        this.personalAffairsApi = personalAffairsApi;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void cancelRequest() {

    }

    @Override
    public void submitAddress(Map<String, String> params) {
        httpManager.request(personalAffairsApi.submitAddress(params), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.OpreateSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.OpreateErr(errorMsg);
            }
        });

    }

    @Override
    public void deleteAddress(Map<String, String> params) {
        httpManager.request(personalAffairsApi.deleteAddress(params), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.OpreateSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.OpreateErr(errorMsg);
            }
        });

    }

    @Override
    public void edtiAddress(Map<String, String> params) {
        httpManager.request(personalAffairsApi.editeAddress(params), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.OpreateSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.OpreateErr(errorMsg);
            }
        });
    }
}
