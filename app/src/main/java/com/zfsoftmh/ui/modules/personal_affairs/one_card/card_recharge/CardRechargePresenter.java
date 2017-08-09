package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.pay.entity.SignInfo;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 业务逻辑
 */

class CardRechargePresenter implements CardRechargeContract.Presenter {

    private CardRechargeContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    CardRechargePresenter(CardRechargeContract.View view, PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void getSign(String orderParam, String biz_content_android) {

        view.showDialog(Constant.loading);
        httpManager.request(personalAffairsApi.getSign(orderParam, biz_content_android),
                compositeDisposable, view, new CallBackListener<SignInfo>() {
                    @Override
                    public void onSuccess(SignInfo data) {
                        view.hideDialog();
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideDialog();
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
