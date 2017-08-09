package com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.MobileLearningApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 借阅信息业务逻辑
 */

class BorrowInformationPresenter implements BorrowInformationContract.Presenter {

    private BorrowInformationContract.View view;
    private MobileLearningApi mobileLearningApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    BorrowInformationPresenter(BorrowInformationContract.View view, MobileLearningApi mobileLearningApi,
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
