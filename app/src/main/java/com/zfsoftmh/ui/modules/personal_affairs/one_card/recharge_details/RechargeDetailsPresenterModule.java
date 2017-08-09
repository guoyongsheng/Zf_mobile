package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 提供实例
 */

@Module
public class RechargeDetailsPresenterModule {

    private RechargeDetailsContract.View view;

    public RechargeDetailsPresenterModule(RechargeDetailsContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    RechargeDetailsPresenter provideRechargeDetailsPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {

        return new RechargeDetailsPresenter(view, personalAffairsApi, httpManager);
    }


    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
