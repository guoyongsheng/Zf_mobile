package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 提供PersonalFilesPresenter 的实例
 */

@Module
public class PersonalFilesPresenterModule {

    private PersonalFilesContract.View view;

    public PersonalFilesPresenterModule(PersonalFilesContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    PersonalFilesPresenter providePersonalFilesPresenter(PersonalAffairsApi personalAffairsApi,
            HttpManager httpManager) {
        return new PersonalFilesPresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
