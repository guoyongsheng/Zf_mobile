package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.CloudNoteInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description: 笔记列表业务处理
 */

public class CloudNoteListPresenter implements CloudNoteListContract.Presenter {

    private CloudNoteListContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public CloudNoteListPresenter(CloudNoteListContract.View view, OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
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
    public void loadLabel() {
//        view.createLoadingDialog(Constant.loading);
        httpManager.request(officeAffairsApi.getNoteLabelData(),
                compositeDisposable, view, new CallBackListener<List<NoteLabelItemInfo>>() {
            @Override
            public void onSuccess(List<NoteLabelItemInfo> data) {
                view.setLabelData(data);
//                view.getCloudNoteListData();
            }

            @Override
            public void onError(String errorMsg) {
//                view.hideUpLoadingDialog();
                view.loadFailure(errorMsg);
            }
        });
    }

    @Override
    public void loadCloudNoteList(Map<String, String> params) {
        httpManager.request(officeAffairsApi.getCloudNoteList(params),
                compositeDisposable, view, new CallBackListener<CloudNoteInfo>() {
            @Override
            public void onSuccess(CloudNoteInfo data) {
//                view.hideUpLoadingDialog();
                view.setNoteListData(data);
            }

            @Override
            public void onError(String errorMsg) {
//                view.hideUpLoadingDialog();
                view.loadFailure(errorMsg);
            }
        });
    }

    @Override
    public void deleteNote(String noteName) {
        view.createLoadingDialog(Constant.loading);
        httpManager.request(officeAffairsApi.deleteNote(noteName), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.hideUpLoadingDialog();
                view.deleteNoteSuccess();

            }

            @Override
            public void onError(String errorMsg) {
                view.hideUpLoadingDialog();
                view.upLoadFailure(Constant.delete_note_failure);
            }
        });
    }
}
