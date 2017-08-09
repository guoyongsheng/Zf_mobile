package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by li
 * on 2017/7/31.
 */

@Module
public class OrderSubmitModule {
    OrderSubmitContract.View view;

    public OrderSubmitModule(OrderSubmitContract.View view){
        this.view=view;
    }
    @PerActivity
    @Provides
    OrderSubmitPresenter provideOrderPresenter(HttpManager httpManager
            , PersonalAffairsApi personalAffairsApi){
        return  new OrderSubmitPresenter(view,httpManager,personalAffairsApi);

    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit){
        return retrofit.create(PersonalAffairsApi.class);
    }

}
