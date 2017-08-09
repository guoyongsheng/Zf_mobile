package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.RequestBody;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 失物招领业务逻辑
 */

public class LostAndFoundPresenter implements LostAndFoundContract.Presenter {

    private LostAndFoundContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    LostAndFoundPresenter(LostAndFoundContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void submit(Map<String, RequestBody> map, Map<String, RequestBody> file) {

        view.showProgress(Constant.SUBMITTING);
        httpManager.request(officeAffairsApi.submitLostAndFound(map, file),
                compositeDisposable, view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideProgress();
                        view.submitSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideProgress();
                        view.submitFailure(errorMsg);
                    }
                });

    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
