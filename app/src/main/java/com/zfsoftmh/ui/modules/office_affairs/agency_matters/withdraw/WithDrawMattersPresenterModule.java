package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 提供实例
 */


@Module
public class WithDrawMattersPresenterModule {

    private WithDrawMattersContract.View view;

    public WithDrawMattersPresenterModule(WithDrawMattersContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    WithDrawMattersPresenter provideWithDrawMattersPresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {
        return new WithDrawMattersPresenter(view, officeAffairsApi, httpManager);
    }


    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
