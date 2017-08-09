package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.SchoolPortalApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 提供实例
 */

@Module
class SubscriptionCenterPresenterModule {

    private SubscriptionCenterContract.View view;


    SubscriptionCenterPresenterModule(SubscriptionCenterContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SubscriptionCenterPresenter provideSubscriptionCenterPresenter(SchoolPortalApi schoolPortalApi,
            HttpManager httpManager) {

        return new SubscriptionCenterPresenter(view, schoolPortalApi, httpManager);
    }


    @Provides
    SchoolPortalApi provideSchoolPortalApi(Retrofit retrofit) {
        return retrofit.create(SchoolPortalApi.class);
    }

}
