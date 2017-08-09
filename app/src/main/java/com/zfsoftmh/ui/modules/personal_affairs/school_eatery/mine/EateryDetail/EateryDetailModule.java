package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ljq on 2017/7/25.
 */
@Module
public class EateryDetailModule {
    EateryDetailContract.View view;

    public EateryDetailModule(EateryDetailContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    EateryDetailPresenter provideEateryDetailPresenter(HttpManager httpManager, PersonalAffairsApi personalAffairsApi) {
        return new EateryDetailPresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }


}
