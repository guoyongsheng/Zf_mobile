package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.DigitalFileItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 数字档案详情业务逻辑
 */

class DigitalFileDetailPresenter implements DigitalFileDetailContract.Presenter {

    private DigitalFileDetailContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    DigitalFileDetailPresenter(DigitalFileDetailContract.View view, PersonalAffairsApi
            personalAffairsApi, HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getDigitalFileItemInfo(final String id, int start, int size) {

        httpManager.request(personalAffairsApi.getDigitalFileItemInfo(id, start, size),
                compositeDisposable, view, new CallBackListener<ResponseListInfo<DigitalFileItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<DigitalFileItemInfo> data) {

                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg + " " + id);
                    }
                });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
