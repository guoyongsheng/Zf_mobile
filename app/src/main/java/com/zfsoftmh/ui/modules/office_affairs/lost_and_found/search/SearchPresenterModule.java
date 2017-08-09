package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 提供实例
 */

@Module
class SearchPresenterModule {

    private SearchContract.View view;

    SearchPresenterModule(SearchContract.View view){
        this.view = view;
    }


    @PerActivity
    @Provides
    SearchPresenter provideSearchPresenter(OfficeAffairsApi officeAffairsApi, HttpManager httpManager){
        return new SearchPresenter(view, officeAffairsApi, httpManager);
    }


    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(@ForNoEncodeRetrofit Retrofit retrofit){
        return retrofit.create(OfficeAffairsApi.class);
    }
}
