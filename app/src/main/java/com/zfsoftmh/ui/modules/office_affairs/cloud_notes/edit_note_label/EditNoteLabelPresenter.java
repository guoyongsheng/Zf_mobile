package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

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
 * @date: 17/5/23
 * @Description:
 */

public class EditNoteLabelPresenter implements EditNoteLabelContract.Presenter {
    private EditNoteLabelContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public EditNoteLabelPresenter(EditNoteLabelContract.View view,
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

    /**
     * 获取标签数据
     */
    @Override
    public void loadData() {
        view.showLoadingDialog(Constant.loading);
        httpManager.request(officeAffairsApi.getNoteLabelData(), compositeDisposable, view, new CallBackListener<List<NoteLabelItemInfo>>() {
            @Override
            public void onSuccess(List<NoteLabelItemInfo> data) {
                view.hideLoadingDialog();
                ArrayList<NoteLabelItemInfo> list = new ArrayList<NoteLabelItemInfo>();
                list = (ArrayList<NoteLabelItemInfo>) data;
                view.setDataList(list);

            }

            @Override
            public void onError(String errorMsg) {
                view.hideLoadingDialog();
            }
        });
    }

    /**
     * 提交标签修改数据
     *
     * @param memoCatalogNameList  标签名
     * @param memoCatalogColorList 标签颜色值
     */
    @Override
    public void submitData(String memoCatalogNameList, String memoCatalogColorList) {
        view.showLoadingDialog(Constant.loading);
        httpManager.request(officeAffairsApi.submitNoteLabel(memoCatalogNameList, memoCatalogColorList),
                compositeDisposable, view, new CallBackListener<List<NoteLabelItemInfo>>() {
                    @Override
                    public void onSuccess(List<NoteLabelItemInfo> data) {
                        view.hideLoadingDialog();
                        ArrayList<NoteLabelItemInfo> list = new ArrayList<NoteLabelItemInfo>();
                        list = (ArrayList<NoteLabelItemInfo>) data;
                        view.setDataList(list);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideLoadingDialog();

                    }
                });
    }
}
