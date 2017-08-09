package com.zfsoftmh.ui.modules.common.home.contacts;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 提供presenter的实例
 */

@Module
public class ContactsPresenterModule {

    private ContactsContract.View view;

    public ContactsPresenterModule(ContactsContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    ContactsPresenter provideContactsPresenter(@Named("ForContactsFragment") PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        return new ContactsPresenter(view, personalAffairsApi, httpManager);
    }

    @Named("ForContactsFragment")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
