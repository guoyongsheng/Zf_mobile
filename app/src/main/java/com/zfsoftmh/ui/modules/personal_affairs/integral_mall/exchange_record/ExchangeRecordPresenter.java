package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

public class ExchangeRecordPresenter implements ExchangeRecordContract.Presenter {
    private ExchangeRecordContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi affairsApi;
    private CompositeDisposable compositeDisposable;

    public ExchangeRecordPresenter(ExchangeRecordContract.View view, HttpManager httpManager,
                                   PersonalAffairsApi affairsApi) {
        this.view = view;
        this.httpManager = httpManager;
        this.affairsApi = affairsApi;
        this.compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void getExchangeRecord(Map<String, String> params) {
        httpManager.request(affairsApi.getExchangeRecordList(params), compositeDisposable,
                view, new CallBackListener<ResponseListInfo<ExchangeRecordItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<ExchangeRecordItemInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
