package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description:
 */

public class IntegralGoodsSearchPresenter implements IntegralGoodsSearchContract.Presenter {
    private IntegralGoodsSearchContract.View view;
    private PersonalAffairsApi affairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    public IntegralGoodsSearchPresenter(IntegralGoodsSearchContract.View view,
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
     * 获取搜索商品列表
     *
     * @param params
     */
    @Override
    public void getIntegralGoodsList(Map<String, String> params) {
        httpManager.request(affairsApi.getIntegralGoodsList(params), compositeDisposable,
                view, new CallBackListener<ResponseListInfo<IntegralMallGoodsInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<IntegralMallGoodsInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
