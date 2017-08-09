package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 未发布的业务逻辑
 */

public class UnPublishedPresenter implements UnPublishedContract.Presenter {

    private UnPublishedContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    UnPublishedPresenter(UnPublishedContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager){
        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {

    }
}
