package com.zfsoftmh.ui.modules.common.home.school_circle.my_attention;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 提供实例
 */


@Module
class MyAttentionPresenterModule {

    private MyAttentionContract.View view;

    MyAttentionPresenterModule(MyAttentionContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    MyAttentionPresenter provideMyAttentionPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {
        return new MyAttentionPresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
