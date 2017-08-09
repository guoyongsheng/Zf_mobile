package com.zfsoftmh.ui.modules.common.home.home;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.service.SchoolPortalApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 提供MainPresenter的实例
 */

@Module
public class HomePresenterModule {

    private HomeContract.View view;

    public HomePresenterModule(HomeContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    HomePresenter provideMainPresenter(SchoolPortalApi schoolPortalApi, @Named("ForNoEncode")
            SchoolPortalApi noEncodeSchoolPortalApi, @Named("ForHomeFragment")
            CommonApi commonApi, HttpManager httpManager) {
        return new HomePresenter(view, schoolPortalApi, noEncodeSchoolPortalApi, commonApi, httpManager);
    }

    @Provides
    SchoolPortalApi provideSchoolPortalApi(Retrofit retrofit) {
        return retrofit.create(SchoolPortalApi.class);
    }

    @Named("ForHomeFragment")
    @Provides
    CommonApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(CommonApi.class);
    }

    @Named("ForNoEncode")
    @Provides
    SchoolPortalApi provideNoEncodeSchoolPortalApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(SchoolPortalApi.class);
    }
}
