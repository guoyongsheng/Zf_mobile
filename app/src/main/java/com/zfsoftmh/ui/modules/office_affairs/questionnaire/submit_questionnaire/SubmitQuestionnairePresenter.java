package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 业务逻辑
 */

class SubmitQuestionnairePresenter implements SubmitQuestionnaireContract.Presenter {

    private SubmitQuestionnaireContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    SubmitQuestionnairePresenter(SubmitQuestionnaireContract.View view,
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {

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
    public void submitQuestionnaire(String value) {

        view.showDialog(Constant.SUBMITTING);
        httpManager.request(officeAffairsApi.submitQuestionnaire(value), compositeDisposable,
                view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideDialog();
                        view.submitSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideDialog();
                        view.submitFailure(errorMsg);
                    }
                });
    }
}
