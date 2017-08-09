package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 提供已办事宜的实例
 */

@Module
public class HasBeenDoneMattersPresenterModule {

    private HasBeenDoneMattersContract.View view;

    public HasBeenDoneMattersPresenterModule(HasBeenDoneMattersContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    HasBeenDoneMattersPresenter provideHasBeenDoneMattersPresenter(@Named("ForHasBeenDoneMatters")
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new HasBeenDoneMattersPresenter(view, officeAffairsApi, httpManager);
    }

    @Named("ForHasBeenDoneMatters")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
