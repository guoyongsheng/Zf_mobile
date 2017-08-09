package com.zfsoftmh.ui.modules.common.splash;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.CommonApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 提供SplashPresenter实例
 */

@Module
class SplashPresenterModule {

    private SplashContract.View view;

    SplashPresenterModule(SplashContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SplashPresenter provideSplashPresenter(CommonApi commonApi, HttpManager httpManager) {
        return new SplashPresenter(view, commonApi, httpManager);
    }

    @Provides
    CommonApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(CommonApi.class);
    }
}
