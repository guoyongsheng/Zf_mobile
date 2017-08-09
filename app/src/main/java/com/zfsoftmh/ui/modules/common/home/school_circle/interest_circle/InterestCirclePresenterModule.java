package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: 提供实例
 */


@Module
class InterestCirclePresenterModule {

    private InterestCircleContract.View view;

    InterestCirclePresenterModule(InterestCircleContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    InterestCirclePresenter provideInterestCirclePresenter(@Named("for_interest_circle")
            PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        return new InterestCirclePresenter(view, personalAffairsApi, httpManager);
    }

    @Named("for_interest_circle")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
