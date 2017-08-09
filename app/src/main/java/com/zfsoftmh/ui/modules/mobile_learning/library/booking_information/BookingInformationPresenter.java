package com.zfsoftmh.ui.modules.mobile_learning.library.booking_information;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.MobileLearningApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 预约查询业务逻辑
 */

class BookingInformationPresenter implements BookingInformationContract.Presenter {

    private BookingInformationContract.View view;
    private MobileLearningApi mobileLearningApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    BookingInformationPresenter(BookingInformationContract.View view, MobileLearningApi mobileLearningApi,
            HttpManager httpManager) {
        this.view = view;
        this.mobileLearningApi = mobileLearningApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void loadData() {

    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
