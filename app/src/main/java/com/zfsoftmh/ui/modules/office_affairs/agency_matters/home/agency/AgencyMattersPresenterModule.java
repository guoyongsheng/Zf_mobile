package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 提供待办事宜的实例
 */

@Module
public class AgencyMattersPresenterModule {

    private AgencyMattersContract.View view;

    public AgencyMattersPresenterModule(AgencyMattersContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    AgencyMattersPresenter provideAgencyMattersPresenter(@Named("ForAgencyMatters") OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new AgencyMattersPresenter(view, officeAffairsApi, httpManager);
    }

    @Named("ForAgencyMatters")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
