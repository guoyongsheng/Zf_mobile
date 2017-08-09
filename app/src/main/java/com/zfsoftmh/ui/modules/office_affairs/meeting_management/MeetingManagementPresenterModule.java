package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 提供实例
 */

@Module
class MeetingManagementPresenterModule {

    private MeetingManagementContract.View view;

    MeetingManagementPresenterModule(MeetingManagementContract.View view){
        this.view = view;
    }

    @PerActivity
    @Provides
    MeetingManagementPresenter provideMeetingManagementPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager){

        return new MeetingManagementPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit){
        return retrofit.create(OfficeAffairsApi.class);
    }
}
