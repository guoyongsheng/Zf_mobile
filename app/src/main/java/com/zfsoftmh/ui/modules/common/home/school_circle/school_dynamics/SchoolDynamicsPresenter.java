package com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.SchoolPortalApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 校园动态的业务逻辑
 */

class SchoolDynamicsPresenter implements SchoolDynamicsContract.Presenter {

    private SchoolDynamicsContract.View view;
    private SchoolPortalApi schoolPortalApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    SchoolDynamicsPresenter(SchoolDynamicsContract.View view, SchoolPortalApi schoolPortalApi,
            HttpManager httpManager) {

        this.view = view;
        this.schoolPortalApi = schoolPortalApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
