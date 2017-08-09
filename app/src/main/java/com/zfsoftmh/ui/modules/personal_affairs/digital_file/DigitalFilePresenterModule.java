package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-15
 * @Description: 提供实例
 */

@Module
class DigitalFilePresenterModule {

    private DigitalFileContract.View view;

    DigitalFilePresenterModule(DigitalFileContract.View view){
        this.view = view;
    }


    @PerActivity
    @Provides
    DigitalFilePresenter provideDigitalFilePresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager){

        return new DigitalFilePresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit){
        return retrofit.create(PersonalAffairsApi.class);
    }
}
