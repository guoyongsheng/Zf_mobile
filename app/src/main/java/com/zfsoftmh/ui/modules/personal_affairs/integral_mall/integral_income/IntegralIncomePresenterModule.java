package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

@Module
class IntegralIncomePresenterModule {
    private IntegralIncomeContract.View view;

    public IntegralIncomePresenterModule(IntegralIncomeContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    IntegralIncomePresenter provideIntegralIncomePresenter(PersonalAffairsApi affairsApi,
                                                           HttpManager httpManager) {
        return new IntegralIncomePresenter(view, httpManager, affairsApi);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }

}
