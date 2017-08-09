package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.IntegralRankingInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description:
 */

public class IntegralRankingPresenter implements IntegralRankingContract.Presenter {
    private IntegralRankingContract.View view;
    private PersonalAffairsApi affairsApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    public IntegralRankingPresenter(IntegralRankingContract.View view,
                                    PersonalAffairsApi affairsApi, HttpManager httpManager) {
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

    /**
     * 获取积分排名信息
     */
    @Override
    public void getIntegralRankingList() {
        view.createLoadingDialog(Constant.loading);
        httpManager.request(affairsApi.getIntegralRankingInfo(), compositeDisposable,
                view, new CallBackListener<IntegralRankingInfo>() {
                    @Override
                    public void onSuccess(IntegralRankingInfo data) {
                        view.hideUpLoadingDialog();
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadingDialog();
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
