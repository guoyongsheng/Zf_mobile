package com.zfsoftmh.ui.modules.school_portal.school_map;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.SchoolPortalApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ljq on 2017/6/28.
 */
@Module
 class SchoolMapPresentModule {
    private SchoolMapContract.View view;


    public SchoolMapPresentModule(SchoolMapContract.View view){
        this.view=view;

    }

    @PerActivity
    @Provides
    SchoolMapPresenter provideSchoolMapPresenter(SchoolPortalApi schoolPortalApi, HttpManager httpManager){
        return new SchoolMapPresenter(view,schoolPortalApi,httpManager);
    }

    @Provides
    SchoolPortalApi provideSchoolPortalApi(Retrofit retrofit){
        return retrofit.create(SchoolPortalApi.class);
    }





}
