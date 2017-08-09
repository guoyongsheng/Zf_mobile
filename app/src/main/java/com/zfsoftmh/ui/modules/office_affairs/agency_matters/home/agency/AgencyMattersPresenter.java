package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 业务逻辑
 */

public class AgencyMattersPresenter implements AgencyMattersContract.Presenter {

    private AgencyMattersContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public AgencyMattersPresenter(AgencyMattersContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
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

    @Override
    public void loadData(int start, int size, String sign) {

        httpManager.request(officeAffairsApi.getAgencyMattersData(String.valueOf(start), String.valueOf(size), sign),
                compositeDisposable, view, new CallBackListener<AgencyMattersInfo>() {
                    @Override
                    public void onSuccess(AgencyMattersInfo data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
