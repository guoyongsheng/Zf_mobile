package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * 创建时间： 2017/5/2 0002
 * 编写人：王世美
 * 功能描述：
 */

@Module
class AddCloudNotePresenterModule {
    private AddCloudNoteContract.View view;

    public AddCloudNotePresenterModule(AddCloudNoteContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    AddCloudNotePresenter provideAddCloudNotePresenter(OfficeAffairsApi commonApi, HttpManager httpManager) {
        return new AddCloudNotePresenter(view, commonApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
