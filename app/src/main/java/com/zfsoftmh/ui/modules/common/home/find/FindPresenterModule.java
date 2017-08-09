package com.zfsoftmh.ui.modules.common.home.find;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.SchoolPortalApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 提供实例
 */

@Module
public class FindPresenterModule {

    private FindContract.View view;

    public FindPresenterModule(FindContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    FindPresenter provideFindPresenter(@Named("for_find_fragment") SchoolPortalApi schoolPortalApi,
            HttpManager httpManager) {
        return new FindPresenter(view, schoolPortalApi, httpManager);
    }

    @Named("for_find_fragment")
    @Provides
    SchoolPortalApi provideSchoolPortalApi(Retrofit retrofit) {
        return retrofit.create(SchoolPortalApi.class);
    }
}
