package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/7/14
 * @Description: 兑换记录详情
 */

@Module
class ExchangeRecordDetailPresenterModule {
    private ExchangeRecordDetailContract.View view;

    public ExchangeRecordDetailPresenterModule(ExchangeRecordDetailContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    ExchangeRecordDetailPresenter provideExchangeRecordDetailPresenter(PersonalAffairsApi affairsApi,
                                                                       HttpManager httpManager) {
        return new ExchangeRecordDetailPresenter(view, affairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
