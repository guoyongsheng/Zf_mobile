package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: 提供实例
 */

@Module
class ScheduleManagementPresenterModule {

    private ScheduleManagementContract.View view;

    ScheduleManagementPresenterModule(ScheduleManagementContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    ScheduleManagementPresenter provideScheduleManagementPresenter(HttpManager httpManager,
            OfficeAffairsApi officeAffairsApi) {
        return new ScheduleManagementPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
