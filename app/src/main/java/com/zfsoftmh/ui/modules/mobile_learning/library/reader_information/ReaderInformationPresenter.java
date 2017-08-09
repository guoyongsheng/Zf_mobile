package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.ReaderInformationInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.MobileLearningApi;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 业务逻辑
 */

class ReaderInformationPresenter implements ReaderInformationContract.Presenter {

    private ReaderInformationContract.View view;
    private MobileLearningApi mobileLearningApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    ReaderInformationPresenter(ReaderInformationContract.View view, MobileLearningApi mobileLearningApi,
            HttpManager httpManager) {

        this.view = view;
        this.mobileLearningApi = mobileLearningApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void loadData() {

        httpManager.request(mobileLearningApi.getReaderInfo(), compositeDisposable, view,
                new CallBackListener<List<ReaderInformationInfo>>() {
                    @Override
                    public void onSuccess(List<ReaderInformationInfo> data) {

                    }

                    @Override
                    public void onError(String errorMsg) {

                    }
                });
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
