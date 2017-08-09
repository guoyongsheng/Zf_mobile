package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.AppCenterInfo;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.SchoolPortalApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 订阅中心业务逻辑
 */

class SubscriptionCenterPresenter implements SubscriptionCenterContract.Presenter {

    private SubscriptionCenterContract.View view;
    private SchoolPortalApi schoolPortalApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    SubscriptionCenterPresenter(SubscriptionCenterContract.View view, SchoolPortalApi schoolPortalApi,
            HttpManager httpManager) {

        this.view = view;
        this.schoolPortalApi = schoolPortalApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void getAppCenterInfo() {

        httpManager.request(schoolPortalApi.getAppCenterInfo(), compositeDisposable, view,
                new CallBackListener<List<AppCenterInfo>>() {
                    @Override
                    public void onSuccess(List<AppCenterInfo> data) {
                        dealData(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }

    @Override
    public void dealData(List<AppCenterInfo> data) {

        Observable.just(data)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<AppCenterInfo>, ArrayList<AppCenterItemInfo>>() {
                    @Override
                    public ArrayList<AppCenterItemInfo> apply(@NonNull List<AppCenterInfo> appCenterIfs) throws Exception {

                        if (appCenterIfs == null) {
                            return null;
                        }
                        ArrayList<AppCenterItemInfo> listAppCenterItemInfo = new ArrayList<>();
                        int size = appCenterIfs.size();
                        for (int i = 0; i < size; i++) {
                            AppCenterInfo appCenterInfo = appCenterIfs.get(i);
                            //标题
                            String title = appCenterInfo.getSystemName();
                            AppCenterItemInfo appCenterItemInfo = new AppCenterItemInfo();
                            appCenterItemInfo.setLocalName(title);
                            appCenterItemInfo.setLocalType(2);
                            listAppCenterItemInfo.add(appCenterItemInfo);

                            //内容
                            List<AppCenterItemInfo> listItem = appCenterInfo.getServiceEntityList();
                            int childSize = listItem.size();
                            for (int j = 0; j < childSize; j++) {
                                AppCenterItemInfo info = listItem.get(j);
                                info.setLocalType(3);
                                listAppCenterItemInfo.add(info);
                            }
                        }
                        return listAppCenterItemInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<AppCenterItemInfo>>() {
                    @Override
                    public void accept(@NonNull ArrayList<AppCenterItemInfo> appCenterItemInfo) throws Exception {

                        if (!view.isActive()) {
                            return;
                        }
                        view.loadSuccess(appCenterItemInfo);
                    }
                });
    }

    @Override
    public void submitService(String servicecode) {

        view.showDialog(Constant.SAVING);
        httpManager.request(schoolPortalApi.submitService(servicecode), compositeDisposable, view,
                new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideDialog();
                        view.serviceSubmitSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideDialog();
                        view.serviceSubmitFailure(errorMsg);
                    }
                });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
