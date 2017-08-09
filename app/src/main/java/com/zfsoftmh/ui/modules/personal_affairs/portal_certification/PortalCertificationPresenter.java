package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 业务逻辑
 */

class PortalCertificationPresenter implements PortalCertificationContract.Presenter {

    private PortalCertificationContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    PortalCertificationPresenter(PortalCertificationContract.View view, PersonalAffairsApi
            personalAffairsApi, HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void portal_certification(String url) {

        httpManager.request(personalAffairsApi.portalCertification(url), compositeDisposable,
                view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {

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
