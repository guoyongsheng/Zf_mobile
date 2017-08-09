package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description:
 */

@Module
class FavouritesListPresenterModule {
    private FavouritesListContract.View view;

    public FavouritesListPresenterModule(FavouritesListContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    FavouritesListPresenter provideFavouritesListPresenter(PersonalAffairsApi affairsApi,
                                                           HttpManager httpManager) {
        return new FavouritesListPresenter(view, affairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
