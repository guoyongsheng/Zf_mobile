package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted;

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
public class HasBeenAcceptedPresenterModule {

    private HasBeenAcceptedContract.View view;

    public HasBeenAcceptedPresenterModule(HasBeenAcceptedContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    HasBeenAcceptedPresenter provideHasBeenAcceptedPresenter(@Named("for_has_been_accepted")
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new HasBeenAcceptedPresenter(view, officeAffairsApi, httpManager);
    }

    @Named("for_has_been_accepted")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
