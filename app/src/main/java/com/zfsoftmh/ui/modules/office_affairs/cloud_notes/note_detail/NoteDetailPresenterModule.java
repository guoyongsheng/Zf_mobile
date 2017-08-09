package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description:
 */

@Module
class NoteDetailPresenterModule {
    private NoteDetailContract.View view;

    public NoteDetailPresenterModule(NoteDetailContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    NoteDetailPresenter provideNoteLabelPresenter(OfficeAffairsApi commonApi, HttpManager httpManager) {
        return new NoteDetailPresenter(view, commonApi, httpManager);
    }

    /*加密网络请求*/
    @Provides
    OfficeAffairsApi provideCommonApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
