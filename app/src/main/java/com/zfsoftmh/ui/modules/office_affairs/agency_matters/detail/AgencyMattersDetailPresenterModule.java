package com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 提供presenter的实例
 */

@Module
public class AgencyMattersDetailPresenterModule {

    private AgencyMattersDetailContract.View view;

    public AgencyMattersDetailPresenterModule(AgencyMattersDetailContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    AgencyMattersDetailPresenter provideAgencyMattersDetailPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        return new AgencyMattersDetailPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
