package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/7/19.
 */
@Module
public class OrderFromModule {
    OrderFormContract.View view;

    public OrderFromModule( OrderFormContract.View view){
        this.view=view;
    }

    @PerActivity
    @Provides
    OrderFormPresenter provideOderFormPresenter(@Named("forOrderForm") PersonalAffairsApi personalAffairsApi,
                                                HttpManager httpManager){
        return new OrderFormPresenter(view,personalAffairsApi,httpManager);
    }

    @Named("forOrderForm")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit){
        return  retrofit.create(PersonalAffairsApi.class);
    }



}
