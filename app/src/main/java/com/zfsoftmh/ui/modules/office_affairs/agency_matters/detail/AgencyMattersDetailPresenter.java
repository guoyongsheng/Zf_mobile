package com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 详情页的业务逻辑
 */

class AgencyMattersDetailPresenter implements AgencyMattersDetailContract.Presenter {

    private AgencyMattersDetailContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    AgencyMattersDetailPresenter(AgencyMattersDetailContract.View view, OfficeAffairsApi
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
