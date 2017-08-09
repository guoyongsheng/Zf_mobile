package com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 提供实例
 */

@Module
public class ConsumerDetailsPresenterModule {

    private ConsumerDetailsContract.View view;

    public ConsumerDetailsPresenterModule(ConsumerDetailsContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    ConsumerDetailsPresenter provideConsumerDetailsPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        return new ConsumerDetailsPresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
