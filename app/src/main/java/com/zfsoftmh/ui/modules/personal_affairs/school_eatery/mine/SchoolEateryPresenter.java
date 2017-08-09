package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by li
 * on 2017/7/19.
 */

public class SchoolEateryPresenter implements SchoolEateryContract.Presenter  {
    private SchoolEateryContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi personalAffairsApi;
    private CompositeDisposable compositeDisposable;



    public SchoolEateryPresenter(SchoolEateryContract.View view,PersonalAffairsApi personalAffairsApi,HttpManager httpManager){
        this.view=view;
        this.personalAffairsApi=personalAffairsApi;
        this.httpManager=httpManager;
        compositeDisposable =new CompositeDisposable();
        view.setPresenter(this);



    }


    @Override
    public void cancelRequest() {

    }

    @Override
    public void loadData(Map<String,String> params) {
      httpManager.request(personalAffairsApi.getSchoolEateryInfo(params), compositeDisposable,
                view, new CallBackListener<ResponseListInfo<EateryInfo>>(){
                    @Override
                    public void onSuccess(ResponseListInfo<EateryInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                    view.loadFailure(errorMsg);
                    }
                });

    }



}


