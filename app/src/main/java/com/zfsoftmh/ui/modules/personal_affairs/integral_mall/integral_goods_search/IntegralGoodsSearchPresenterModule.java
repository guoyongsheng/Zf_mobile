package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description:
 */

@Module
class IntegralGoodsSearchPresenterModule {
    private IntegralGoodsSearchContract.View view;

    public IntegralGoodsSearchPresenterModule(IntegralGoodsSearchContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    IntegralGoodsSearchPresenter provideIntegralGoodsSearchPresenter(PersonalAffairsApi affairsApi,
                                                                     HttpManager httpManager) {
        return new IntegralGoodsSearchPresenter(view, affairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
