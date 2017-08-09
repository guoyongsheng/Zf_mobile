package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * 创建时间： 2017/5/11 0011
 * 编写人：王世美
 * 功能描述：提供presenter的实例
 */

@Module
class NoteLabelPresenterModule {
    private NoteLabelContract.View view;

    public NoteLabelPresenterModule(NoteLabelContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    NoteLabelPresenter provideNoteLabelPresenter(OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new NoteLabelPresenter(view,officeAffairsApi,httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit){
        return retrofit.create(OfficeAffairsApi.class);
    }

}
