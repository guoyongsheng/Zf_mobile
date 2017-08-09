package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.CommonApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description:
 */

@Module
class SuggestionsPresenterModule {
    private SuggestionsContract.View view;

    public SuggestionsPresenterModule(SuggestionsContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SuggestionsPresenter provideSuggestionsPresenter(CommonApi commonApi, HttpManager httpManager) {
        return new SuggestionsPresenter(view, commonApi, httpManager);
    }

    @Provides
    CommonApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(CommonApi.class);
    }
}
