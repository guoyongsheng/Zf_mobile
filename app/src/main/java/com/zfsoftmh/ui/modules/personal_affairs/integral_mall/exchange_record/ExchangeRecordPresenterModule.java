package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

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
class ExchangeRecordPresenterModule {
    private ExchangeRecordContract.View view;

    public ExchangeRecordPresenterModule(ExchangeRecordContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    ExchangeRecordPresenter provideExchangeRecordPresenter(PersonalAffairsApi affairsApi, HttpManager httpManager) {
        return new ExchangeRecordPresenter(view, httpManager, affairsApi);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
