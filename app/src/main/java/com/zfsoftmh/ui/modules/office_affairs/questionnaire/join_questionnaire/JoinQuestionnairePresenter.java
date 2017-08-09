package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description:
 */

class JoinQuestionnairePresenter implements JoinQuestionnaireContract.Presenter {

    private JoinQuestionnaireContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    JoinQuestionnairePresenter(JoinQuestionnaireContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void joinQuestionnaire(String answer) {

        view.showDialog(Constant.SUBMITTING);
        httpManager.request(officeAffairsApi.joinQuestionnaire(answer), compositeDisposable,
                view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideDialog();
                        view.joinSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideDialog();
                        view.joinFailure(errorMsg);
                    }
                });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
