package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 提供实例
 */

@Module
public class UnPublishedPresenterModule {

    private UnPublishedContract.View view;

    public UnPublishedPresenterModule(UnPublishedContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    UnPublishedPresenter provideUnPublishedPresenter(@Named("for_un_published") OfficeAffairsApi
            officeAffairsApi, HttpManager httpManager) {
        return new UnPublishedPresenter(view, officeAffairsApi, httpManager);
    }


    @Named("for_un_published")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
