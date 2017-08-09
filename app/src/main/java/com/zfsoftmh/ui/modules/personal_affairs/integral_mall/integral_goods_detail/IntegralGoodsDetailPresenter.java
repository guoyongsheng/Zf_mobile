package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description:
 */

public class IntegralGoodsDetailPresenter implements IntegralGoodsDetailContract.Presenter {
    private IntegralGoodsDetailContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public IntegralGoodsDetailPresenter(IntegralGoodsDetailContract.View view,
                                        PersonalAffairsApi personalAffairsApi,
                                        HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        this.compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    /**
     * 兑换礼品
     *
     * @param params 请求参数
     */
    @Override
    public void giftExchange(Map<String, String> params) {
        view.createLoadingDialog(Constant.loading);
        httpManager.request(personalAffairsApi.exchangeGoods(params), compositeDisposable,
                view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideUpLoadingDialog();
                        view.exchangeGoodsSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadingDialog();
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
