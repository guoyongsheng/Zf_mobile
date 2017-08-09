package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.DigitalFileDepartInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-15
 * @Description: 数字档案的业务逻辑
 */

class DigitalFilePresenter implements DigitalFileContract.Presenter {

    private DigitalFileContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    DigitalFilePresenter(DigitalFileContract.View view, PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void getDigitalFileDepartInfo() {

        httpManager.request(personalAffairsApi.getDigitalFileDepartInfo(), compositeDisposable,
                view, new CallBackListener<List<DigitalFileDepartInfo>>() {
                    @Override
                    public void onSuccess(List<DigitalFileDepartInfo> data) {
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
