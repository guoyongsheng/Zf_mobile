package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: 日程管理业务逻辑
 */

class ScheduleManagementPresenter implements ScheduleManagementContract.Presenter {

    private ScheduleManagementContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    ScheduleManagementPresenter(ScheduleManagementContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
