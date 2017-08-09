package com.zfsoftmh.ui.modules.office_affairs.agency_matters.process_tracking;

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
public class ProcessTrackingPresenterModule {

    private ProcessTrackingContract.View view;

    public ProcessTrackingPresenterModule(ProcessTrackingContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    ProcessTrackingPresenter provideProcessTrackingPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
        return new ProcessTrackingPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
