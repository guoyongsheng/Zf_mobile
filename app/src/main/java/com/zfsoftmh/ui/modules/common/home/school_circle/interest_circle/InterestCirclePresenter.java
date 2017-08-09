package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: 兴趣圈业务逻辑
 */

class InterestCirclePresenter implements InterestCircleContract.Presenter {

    private InterestCircleContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    InterestCirclePresenter(InterestCircleContract.View view, PersonalAffairsApi
            personalAffairsApi, HttpManager httpManager) {

        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void getMyCircle(int start, int size) {

    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
