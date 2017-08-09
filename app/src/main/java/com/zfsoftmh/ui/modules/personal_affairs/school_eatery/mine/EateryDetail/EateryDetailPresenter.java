package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.FoodCataListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ljq
 * on 2017/7/25.
 */

class EateryDetailPresenter  implements EateryDetailContract.Presenter  {
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;
    private  PersonalAffairsApi personalAffairsApi;
    private EateryDetailContract.View view;



   EateryDetailPresenter(EateryDetailContract.View view,PersonalAffairsApi personalAffairsApi
           ,HttpManager httpManager){
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
    public void loadData(String eateryId) {
       httpManager.request(personalAffairsApi.getFoodInfo(eateryId), compositeDisposable, view,
               new CallBackListener<List<FoodCataListInfo>>() {
                   @Override
                   public void onSuccess(List<FoodCataListInfo> data) {
                       view.getFoodSucess(data);
                   }

                   @Override
                   public void onError(String errorMsg) {

                   }
               });


    }
}
