package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
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
public class PortalCertificationPresenterModule {

    private PortalCertificationContract.View view;

    PortalCertificationPresenterModule(PortalCertificationContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    PortalCertificationPresenter providePortalCertificationPresenter(PersonalAffairsApi
            personalAffairsApi, HttpManager httpManager) {

        return new PortalCertificationPresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
