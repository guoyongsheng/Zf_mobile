package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.PersonalFilesInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 业务逻辑
 */

class PersonalFilesPresenter implements PersonalFilesContract.Presenter {

    private PersonalFilesContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi personalAffairsApi;
    private CompositeDisposable compositeDisposable;

    PersonalFilesPresenter(PersonalFilesContract.View view,
            PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
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
    public void loadData() {
        httpManager.request(personalAffairsApi.getPersonalFilesInfo(), compositeDisposable, view,
                new CallBackListener<List<PersonalFilesInfo>>() {
                    @Override
                    public void onSuccess(List<PersonalFilesInfo> data) {
                        view.showData(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.showErrorMsg(errorMsg);
                    }
                });
    }

    @Override
    public void loadDetailData(String id, String account, final int position) {
        cancelRequest();
        httpManager.request(personalAffairsApi.getPersonalFilesDetailInfo(id, account),
                compositeDisposable, view, new CallBackListener<List<List<Map<String, String>>>>() {
                    @Override
                    public void onSuccess(List<List<Map<String, String>>> data) {
                        view.showDetailData(data, position);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.showErrorMsg(errorMsg);
                    }
                });
    }
}
