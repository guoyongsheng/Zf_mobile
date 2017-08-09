package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.MyMessageItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public class MyMessagePresenter implements MyMessageContract.Presenter {
    private MyMessageContract.View view;
    private HttpManager httpManager;
    private PersonalAffairsApi affairsApi;
    private CompositeDisposable compositeDisposable;

    public MyMessagePresenter(MyMessageContract.View view, HttpManager httpManager,
                              PersonalAffairsApi affairsApi) {
        this.view = view;
        this.httpManager = httpManager;
        this.affairsApi = affairsApi;
        this.compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void getMyMessageList(Map<String, String> params) {
        httpManager.request(affairsApi.getMyMessageList(params), compositeDisposable,
                view, new CallBackListener<ResponseListInfo<MyMessageItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<MyMessageItemInfo> data) {
                        view.loadMyMessageListSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
