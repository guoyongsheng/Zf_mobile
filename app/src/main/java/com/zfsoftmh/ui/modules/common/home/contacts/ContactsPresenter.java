package com.zfsoftmh.ui.modules.common.home.contacts;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 通讯录的presenter
 */
public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View view;   //view层的引用
    private PersonalAffairsApi personalAffairsApi; //model层的引用
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    ContactsPresenter(ContactsContract.View view, PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
