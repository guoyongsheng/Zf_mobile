package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement;

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
 * @Description: 提供办结事宜的实例
 */

@Module
public class SettlementMattersPresenterModule {

    private SettlementMattersContract.View view;

    public SettlementMattersPresenterModule(SettlementMattersContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    SettlementMattersPresenter provideSettlementMattersPresenter(@Named("FofSettlementMatters")
            OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        return new SettlementMattersPresenter(view, officeAffairsApi, httpManager);
    }

    @Named("FofSettlementMatters")
    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }

}
