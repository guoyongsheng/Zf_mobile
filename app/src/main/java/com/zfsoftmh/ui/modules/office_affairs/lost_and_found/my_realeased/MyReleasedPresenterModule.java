package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 提供实例
 */

@Module
class MyReleasedPresenterModule {

    private MyReleasedContract.View view;

    MyReleasedPresenterModule(MyReleasedContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    MyReleasedPresenter provideMyReleasedPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
        return new MyReleasedPresenter(view, officeAffairsApi, httpManager);
    }


    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
