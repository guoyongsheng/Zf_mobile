package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.PersonalAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 提供实例
 */

@Module
public class CardRechargePresenterModule {

    private CardRechargeContract.View view;

    public CardRechargePresenterModule(CardRechargeContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    CardRechargePresenter provideCardRechargePresenter(PersonalAffairsApi personalAffairsApi, HttpManager httpManager) {
        return new CardRechargePresenter(view, personalAffairsApi, httpManager);
    }

    @Provides
    PersonalAffairsApi providePersonalAffairsApi(Retrofit retrofit){
        return retrofit.create(PersonalAffairsApi.class);
    }

}
