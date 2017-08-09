package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 业务逻辑
 */

public class SettlementMattersPresenter implements SettlementMattersContract.Presenter {

    private SettlementMattersContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public SettlementMattersPresenter(SettlementMattersContract.View view, OfficeAffairsApi
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

    @Override
    public void loadData(int start, int size) {

        httpManager.request(officeAffairsApi.getSettlementMattersData(String.valueOf(start),
                String.valueOf(size)), compositeDisposable, view, new CallBackListener<List<AgencyMattersInfo>>() {

            @Override
            public void onSuccess(List<AgencyMattersInfo> data) {
                view.loadSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.loadFailure(errorMsg);
            }
        });
    }
}
