package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by li
 * on 2017/8/2.
 */

@Module
public class OrderDetailModule {
    OrderDetailContract.View view;


    public OrderDetailModule(OrderDetailContract.View view){
        this.view=view;

    }
    @PerActivity
    @Provides
    public OrderDetailPresenter proviedeOrderDetailPresenter(HttpManager httpManager, PersonalAffairsApi personalAffairsApi
                                                             ){
        return  new OrderDetailPresenter(view,personalAffairsApi,httpManager);
    }

    @Provides
    public PersonalAffairsApi providePersonalAffairApi(Retrofit retrofit){
        return retrofit.create(PersonalAffairsApi.class);

    }

}
