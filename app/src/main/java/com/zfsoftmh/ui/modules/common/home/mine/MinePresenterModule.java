package com.zfsoftmh.ui.modules.common.home.mine;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.ForNoEncodeRetrofit;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 提供MinePresenter的实例
 */

@Module
public class MinePresenterModule {

    private MineContract.View view;

    public MinePresenterModule(MineContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    MinePresenter provideMinePresenter(@Named("ForMineFragment") PersonalAffairsApi personalAffairsApi,
            @Named("ForMineFragmentNoEncode") PersonalAffairsApi personalAffairsNoEncodeApi, HttpManager httpManager) {
        return new MinePresenter(view, personalAffairsApi, personalAffairsNoEncodeApi, httpManager);
    }

    @Named("ForMineFragment")
    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }

    @Named("ForMineFragmentNoEncode")
    @Provides
    PersonalAffairsApi providePersonAffairsNoEncodeApi(@ForNoEncodeRetrofit Retrofit retrofit) {
        return retrofit.create(PersonalAffairsApi.class);
    }

}
