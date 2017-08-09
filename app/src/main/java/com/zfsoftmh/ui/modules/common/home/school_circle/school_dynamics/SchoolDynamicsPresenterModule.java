package com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.SchoolPortalApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 提供实例
 */

@Module
class SchoolDynamicsPresenterModule {

    private SchoolDynamicsContract.View view;

    SchoolDynamicsPresenterModule(SchoolDynamicsContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SchoolDynamicsPresenter provideSchoolDynamicsPresenter(SchoolPortalApi schoolPortalApi,
            HttpManager httpManager) {

        return new SchoolDynamicsPresenter(view, schoolPortalApi, httpManager);
    }

    @Provides
    SchoolPortalApi provideSchoolPortalApi(Retrofit retrofit) {
        return retrofit.create(SchoolPortalApi.class);
    }
}
