package com.zfsoftmh.ui.modules.mobile_learning.library.arrears_information;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.MobileLearningApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 欠费查询业务逻辑
 */

class ArrearsInformationPresenter implements ArrearsInformationContract.Presenter {

    private ArrearsInformationContract.View view;
    private MobileLearningApi mobileLearningApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    ArrearsInformationPresenter(ArrearsInformationContract.View view, MobileLearningApi mobileLearningApi,
            HttpManager httpManager) {

        this.view = view;
        this.mobileLearningApi = mobileLearningApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
