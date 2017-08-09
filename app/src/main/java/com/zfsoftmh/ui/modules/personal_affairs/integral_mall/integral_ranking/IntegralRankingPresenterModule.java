package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description:
 */

@Module
class IntegralRankingPresenterModule {
    private IntegralRankingContract.View view;

    public IntegralRankingPresenterModule(IntegralRankingContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    IntegralRankingPresenter provideIntegralRankingPresenter(PersonalAffairsApi affairsApi,
                                                             HttpManager httpManager) {
        return new IntegralRankingPresenter(view, affairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
