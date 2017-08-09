package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 提供失物招领的实例
 */

@Module
public class LostAndFoundPresenterModule {

    private LostAndFoundContract.View view;

    public LostAndFoundPresenterModule(LostAndFoundContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    LostAndFoundPresenter provideLostAndFoundPresenter(@Named("for_lost_and_found") OfficeAffairsApi
            officeAffairsApi, HttpManager httpManager) {
        return new LostAndFoundPresenter(view, officeAffairsApi, httpManager);
    }


    @Named("for_lost_and_found")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
