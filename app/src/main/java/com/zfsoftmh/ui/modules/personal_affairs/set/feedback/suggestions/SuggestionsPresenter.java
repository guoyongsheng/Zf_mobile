package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description:
 */

public class SuggestionsPresenter implements SuggestionsContract.Presenter {

    private SuggestionsContract.View view;
    private CommonApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    public SuggestionsPresenter(SuggestionsContract.View view, CommonApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void uploadSuggestions(Map<String, RequestBody> requestBody, List<MultipartBody.Part> images) {
        view.createUpLoadPicDialog(Constant.suggestions_uploading);
        httpManager.request(commonApi.upLoadFeedBack(requestBody, images), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.hideUpLoadPicDialog();
                view.deleteCameraPic();
                view.skipPage();
            }

            @Override
            public void onError(String errorMsg) {
                view.hideUpLoadPicDialog();
                view.upLoadFailure(Constant.suggestions_upload_failure);
            }
        });

    }
}
