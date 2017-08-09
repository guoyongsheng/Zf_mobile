package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 提供实例
 */

@Module
public class NotAcceptedPresenterModule {

    private NotAcceptedContract.View view;

    public NotAcceptedPresenterModule(NotAcceptedContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    NotAcceptedPresenter provideNotAcceptedPresenter(@Named("for_not_accepted")
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new NotAcceptedPresenter(view, officeAffairsApi, httpManager);
    }

    @Named("for_not_accepted")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
