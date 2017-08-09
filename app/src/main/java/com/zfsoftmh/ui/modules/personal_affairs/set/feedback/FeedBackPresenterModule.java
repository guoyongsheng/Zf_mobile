package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 提供presenter的实例
 */

@Module
class FeedBackPresenterModule {

    private FeedBackContract.View view;

    public FeedBackPresenterModule(FeedBackContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    FeedBackPresenter provideFeedBackPresenter(PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        return new FeedBackPresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
