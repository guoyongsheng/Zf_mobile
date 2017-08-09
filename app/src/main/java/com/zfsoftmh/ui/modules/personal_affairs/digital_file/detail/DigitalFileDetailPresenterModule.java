package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 提供实例
 */

@Module
class DigitalFileDetailPresenterModule {

    private DigitalFileDetailContract.View view;

    DigitalFileDetailPresenterModule(DigitalFileDetailContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    DigitalFileDetailPresenter provideDigitalFileDetailPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        return new DigitalFileDetailPresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
