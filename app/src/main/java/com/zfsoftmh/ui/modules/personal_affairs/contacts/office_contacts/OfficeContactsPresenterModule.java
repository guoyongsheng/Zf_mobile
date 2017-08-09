package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 提供实例
 */

@Module
class OfficeContactsPresenterModule {


    private OfficeContactsContract.View view;

    OfficeContactsPresenterModule(OfficeContactsContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    OfficeContactsPresenter provideOfficeContactsPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {
        return new OfficeContactsPresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
