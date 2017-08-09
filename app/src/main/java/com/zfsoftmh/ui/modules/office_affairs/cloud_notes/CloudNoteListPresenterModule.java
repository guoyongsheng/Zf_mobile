package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description:
 */

@Module
class CloudNoteListPresenterModule {
    private CloudNoteListContract.View view;

    public CloudNoteListPresenterModule(CloudNoteListContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    CloudNoteListPresenter provideCloudNoteListPresenter(OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new CloudNoteListPresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
