package com.zfsoftmh.ui.modules.office_affairs.agency_matters.submit;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/7
 * @Description: 提交待办事宜业务逻辑
 */

public class SubmitMattersPresenter implements SubmitMattersContract.Presenter {

    private SubmitMattersContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public SubmitMattersPresenter(SubmitMattersContract.View view, OfficeAffairsApi
            officeAffairsApi, HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
