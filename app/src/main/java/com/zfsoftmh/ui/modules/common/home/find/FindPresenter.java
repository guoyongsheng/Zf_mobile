package com.zfsoftmh.ui.modules.common.home.find;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.SchoolPortalApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 发现业务逻辑
 */

public class FindPresenter implements FindContract.Presenter {

    private FindContract.View view;
    private SchoolPortalApi schoolPortalApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    FindPresenter(FindContract.View view, SchoolPortalApi schoolPortalApi, HttpManager httpManager) {
        this.view = view;
        this.schoolPortalApi = schoolPortalApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
