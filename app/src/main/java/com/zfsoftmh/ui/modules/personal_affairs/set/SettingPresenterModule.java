package com.zfsoftmh.ui.modules.personal_affairs.set;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 提供presenter的实例
 */

@Module
class SettingPresenterModule {

    private SettingContract.View view;

    SettingPresenterModule(SettingContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SettingPresenter provideSettingPresenter(PersonalAffairsApi personalAffairsApi,
            CommonApi commonApi, HttpManager httpManager) {
        return new SettingPresenter(view, personalAffairsApi, commonApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }

    @Provides
    CommonApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(CommonApi.class);
    }
}
