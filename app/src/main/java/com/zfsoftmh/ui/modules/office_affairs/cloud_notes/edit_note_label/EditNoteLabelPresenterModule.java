package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description:
 */

@Module
class EditNoteLabelPresenterModule {
    private EditNoteLabelContract.View view;

    public EditNoteLabelPresenterModule(EditNoteLabelContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    EditNoteLabelPresenter provideNoteLabelPresenter(
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new EditNoteLabelPresenter(view,officeAffairsApi,httpManager);
    }


    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit){
        return retrofit.create(OfficeAffairsApi.class);
    }
}
