package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.QuestionnairePublishedInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 已发布业务逻辑
 */

public class PublishedPresenter implements PublishedContract.Presenter {

    private PublishedContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    PublishedPresenter(PublishedContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void loadData(int startPage, int pageSize) {

        httpManager.request(officeAffairsApi.getQuestionnaireInfo(startPage, pageSize),
                compositeDisposable, view, new CallBackListener<ResponseListInfo<QuestionnairePublishedInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<QuestionnairePublishedInfo> data) {
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
