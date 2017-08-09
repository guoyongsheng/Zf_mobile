package com.zfsoftmh.ui.modules.office_affairs.agency_matters.submit;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/7
 * @Description: 提供实例
 */

@Module
public class SubmitMattersPresenterModule {

    private SubmitMattersContract.View view;

    public SubmitMattersPresenterModule(SubmitMattersContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    SubmitMattersPresenter provideSubmitMattersPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
        return new SubmitMattersPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
