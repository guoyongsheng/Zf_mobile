package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 提供presenter的实例
 */


@Module
public class OneCardPresenterModule {


    private OneCardContract.View view;

    public OneCardPresenterModule(OneCardContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    OneCardPresenter provideOneCardPresenter(PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        return new OneCardPresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
