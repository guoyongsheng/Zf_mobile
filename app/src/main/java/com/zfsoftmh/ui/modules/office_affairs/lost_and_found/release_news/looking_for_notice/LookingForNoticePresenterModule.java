package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 提供寻物启事的实例
 */

@Module
public class LookingForNoticePresenterModule {

    private LookingForNoticeContract.View view;

    public LookingForNoticePresenterModule(LookingForNoticeContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    LookingForNoticePresenter provideLookingForNoticePresenter(@Named("for_look_for_notice")
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new LookingForNoticePresenter(view, officeAffairsApi, httpManager);
    }


    @Named("for_look_for_notice")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
