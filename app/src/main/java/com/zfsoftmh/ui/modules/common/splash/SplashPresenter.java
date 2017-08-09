package com.zfsoftmh.ui.modules.common.splash;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 业务逻辑
 */
class SplashPresenter implements SplashContract.Presenter {

    private static final String TAG = "SplashPresenter";
    private SplashContract.View view;
    private CommonApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    SplashPresenter(SplashContract.View view, CommonApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void addUpInstallNumber() {

        httpManager.request(commonApi.install(), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                LoggerHelper.e(TAG, " addUpInstallNumber 统计成功!");
            }

            @Override
            public void onError(String errorMsg) {
                LoggerHelper.e(TAG, " addUpInstallNumber 统计失败!");
            }
        });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
