package com.zfsoftmh.ui.modules.common.home.school_circle.my_attention;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 我的关注业务逻辑
 */

class MyAttentionPresenter implements MyAttentionContract.Presenter {

    private MyAttentionContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    MyAttentionPresenter(MyAttentionContract.View view, PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancelRequest() {

    }
}
