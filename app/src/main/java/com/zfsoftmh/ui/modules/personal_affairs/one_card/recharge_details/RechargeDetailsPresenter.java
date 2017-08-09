package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 业务逻辑
 */

class RechargeDetailsPresenter implements RechargeDetailsContract.Presenter {

    private RechargeDetailsContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    RechargeDetailsPresenter(RechargeDetailsContract.View view, PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void loadData(int startPage, int pageSize, String oneCardId) {

        httpManager.request(personalAffairsApi.getOneCardDetailsInfo(startPage, pageSize, oneCardId, "0"),
                compositeDisposable, view, new CallBackListener<ResponseListInfo<OneCardItemDetailsInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<OneCardItemDetailsInfo> data) {
                         view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
