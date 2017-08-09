package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 业务逻辑
 */

class OfficeContactsPresenter implements OfficeContactsContract.Presenter {

    private OfficeContactsContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    OfficeContactsPresenter(OfficeContactsContract.View view, PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
