package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description:
 */

@Module
class IntegralGoodsDetailPresenterModule {
    private IntegralGoodsDetailContract.View view;

    public IntegralGoodsDetailPresenterModule(IntegralGoodsDetailContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    IntegralGoodsDetailPresenter provideIntegralGoodsDetailPresenter(PersonalAffairsApi affairsApi,
                                                                     HttpManager httpManager) {
        return new IntegralGoodsDetailPresenter(view, affairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
