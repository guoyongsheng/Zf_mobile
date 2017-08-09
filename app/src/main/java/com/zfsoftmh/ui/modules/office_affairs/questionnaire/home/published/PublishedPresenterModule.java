package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published;

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
public class PublishedPresenterModule {

    private PublishedContract.View view;

    public PublishedPresenterModule(PublishedContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    PublishedPresenter providePublishedPresenter(@Named("for_published") OfficeAffairsApi
            officeAffairsApi, HttpManager httpManager) {
        return new PublishedPresenter(view, officeAffairsApi, httpManager);
    }


    @Named("for_published")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
