package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ljq
 * on 2017/7/19.
 */
@Module
public class AddressManagerModule {
    AddressManagerContract.View view;

    public AddressManagerModule(AddressManagerContract.View view){
        this.view=view;
    }

    @PerActivity
    @Provides
    AddressManagerPresenter provideAddressManagerPresenter(@Named("foraddressManager") PersonalAffairsApi personalAffairsApi, HttpManager httpManager){
        return new AddressManagerPresenter(view,personalAffairsApi,httpManager);
    }

    @Named("foraddressManager")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit){
        return  retrofit.create(PersonalAffairsApi.class);
    }


}
