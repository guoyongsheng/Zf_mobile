package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.service.PersonalAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: presenter
 */

public class FeedBackPresenter implements FeedBackContract.Presenter {

    private FeedBackContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public FeedBackPresenter(FeedBackContract.View view, PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public boolean checkFeedBackContentIsEmpty(String content) {
        return content == null || content.trim().length() == 0;
    }

    @Override
    public void feedBack(String content) {

        if(checkFeedBackContentIsEmpty(content)){
            view.showErrorMsg(Constant.PLEASE_INPUT_FEED_BACK_CONTENT);
            return;
        }
    }
}
