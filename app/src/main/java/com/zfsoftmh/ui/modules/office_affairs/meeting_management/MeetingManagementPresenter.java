package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.MeetingManagementInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 会议管理业务逻辑
 */

class MeetingManagementPresenter implements MeetingManagementContract.Presenter {

    private MeetingManagementContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    MeetingManagementPresenter(MeetingManagementContract.View view, OfficeAffairsApi
            officeAffairsApi, HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void loadData(int start, int size, int type) {

        httpManager.request(officeAffairsApi.getMeetingManagementInfo(start, size, type),
                compositeDisposable, view, new CallBackListener<ResponseListInfo<MeetingManagementInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<MeetingManagementInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
