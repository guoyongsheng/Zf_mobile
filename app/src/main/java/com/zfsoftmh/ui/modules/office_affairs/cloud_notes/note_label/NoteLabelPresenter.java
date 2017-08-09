package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/5/11
 * @Description: 标签列表业务处理
 */

public class NoteLabelPresenter implements NoteLabelContract.Presenter {

    private NoteLabelContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public NoteLabelPresenter(NoteLabelContract.View view, OfficeAffairsApi officeAffairsApi,
                              HttpManager httpManager) {
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
    public void loadData() {
        view.showLoadingDialog(Constant.loading);
        httpManager.request(officeAffairsApi.getNoteLabelData(), compositeDisposable, view, new CallBackListener<List<NoteLabelItemInfo>>() {
            @Override
            public void onSuccess(List<NoteLabelItemInfo> data) {
                view.hideLoadingDialog();
                view.loadData(data);

            }

            @Override
            public void onError(String errorMsg) {
                view.hideLoadingDialog();
            }
        });
    }
}
