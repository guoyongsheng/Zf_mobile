package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.IntegralIncomeItemInfo;
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

public class IntegralIncomePresenter implements IntegralIncomeContract.Presenter {
    private IntegralIncomeContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi affairsApi;
    private CompositeDisposable compositeDisposable;

    public IntegralIncomePresenter(IntegralIncomeContract.View view,
                                   HttpManager httpManager,
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

    /**
     * 获取积分收入明细
     *
     * @param params
     */
    @Override
    public void getIntegralIncome(Map<String, String> params) {
        httpManager.request(affairsApi.getIntegralIncomeList(params), compositeDisposable,
                view, new CallBackListener<ResponseListInfo<IntegralIncomeItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<IntegralIncomeItemInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);

                    }
                });
    }
}
