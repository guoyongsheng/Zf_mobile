package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.OneCardInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 业务逻辑
 */

public class OneCardPresenter implements OneCardContract.Presenter {

    private OneCardContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public OneCardPresenter(OneCardContract.View view, PersonalAffairsApi personalAffairsApi,
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

    @Override
    public void getAccountBalance() {

        view.startLoading();
        httpManager.request(personalAffairsApi.getAccountBalance(), compositeDisposable, view,
                new CallBackListener<OneCardInfo>() {
                    @Override
                    public void onSuccess(OneCardInfo data) {
                        view.stopLoading();
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.stopLoading();
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
