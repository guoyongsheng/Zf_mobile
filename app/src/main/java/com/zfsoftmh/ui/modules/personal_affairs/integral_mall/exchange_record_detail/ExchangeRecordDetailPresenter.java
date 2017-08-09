package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/7/14
 * @Description:
 */

public class ExchangeRecordDetailPresenter implements ExchangeRecordDetailContract.Presenter {
    private ExchangeRecordDetailContract.View view;
    private PersonalAffairsApi affairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public ExchangeRecordDetailPresenter(ExchangeRecordDetailContract.View view,
                                         PersonalAffairsApi affairsApi,
                                         HttpManager httpManager) {
        this.view = view;
        this.affairsApi = affairsApi;
        this.httpManager = httpManager;
        this.compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void getRecordDetail(String goodsid) {
        view.createLoadingDialog(Constant.loading);
        httpManager.request(affairsApi.getExchangeRecordDetail(goodsid), compositeDisposable, view, new CallBackListener<IntegralMallGoodsInfo>() {
            @Override
            public void onSuccess(IntegralMallGoodsInfo data) {
                view.hideUpLoadingDialog();
                view.loadDetailInfoSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.hideUpLoadingDialog();
                view.loadFailure(errorMsg);
            }
        });
    }
}
