package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 退回待办事宜的业务逻辑
 */

public class WithDrawMattersPresenter implements WithDrawMattersContract.Presenter {

    private WithDrawMattersContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public WithDrawMattersPresenter(WithDrawMattersContract.View view, OfficeAffairsApi
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
