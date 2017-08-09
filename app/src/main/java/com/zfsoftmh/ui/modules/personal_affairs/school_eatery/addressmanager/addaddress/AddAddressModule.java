package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;


import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ljq on 2017/7/20.
 */

@Module
public class AddAddressModule {
    AddAddressContract.View view;

    public AddAddressModule(AddAddressContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    AddAddressPresenter provideAddAddressPresenter(PersonalAffairsApi affairsApi,
                                                   HttpManager httpManager) {
        return new AddAddressPresenter(view, httpManager, affairsApi);

    }

    @Provides
    PersonalAffairsApi providePresonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }


}
