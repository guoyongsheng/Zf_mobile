package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

@Module
class MyMessagePresenterModule {
    private MyMessageContract.View view;

    public MyMessagePresenterModule(MyMessageContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    MyMessagePresenter provideMyMessagePresenter(PersonalAffairsApi affairsApi, HttpManager httpManager) {
        return new MyMessagePresenter(view,httpManager,affairsApi);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }
}
