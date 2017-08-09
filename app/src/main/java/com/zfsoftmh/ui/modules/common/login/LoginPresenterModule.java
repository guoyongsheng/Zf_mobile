package com.zfsoftmh.ui.modules.common.login;

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
 * @Description: 提供presenter的实例
 */

@Module
class LoginPresenterModule {

    private LoginContract.View view;

    LoginPresenterModule(LoginContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    LoginPresenter provideLoginPresenter(CommonApi commonApi, HttpManager httpManager) {
        return new LoginPresenter(view, commonApi, httpManager);
    }

    @Provides
    CommonApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(CommonApi.class);
    }
}
