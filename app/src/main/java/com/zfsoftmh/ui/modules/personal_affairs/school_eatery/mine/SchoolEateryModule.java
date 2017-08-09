package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ljq
 * on 2017/7/19.
 */

@Module
public class SchoolEateryModule {
    SchoolEateryContract.View view;

    public SchoolEateryModule(SchoolEateryContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    SchoolEateryPresenter provideSchoolEateryPresenter(@Named("forSchooleaterymain") PersonalAffairsApi personalAffairsApi,
                                                       HttpManager httpManager) {
        return new SchoolEateryPresenter(view, personalAffairsApi, httpManager);
    }
    @Named ("forSchooleaterymain")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }


}
