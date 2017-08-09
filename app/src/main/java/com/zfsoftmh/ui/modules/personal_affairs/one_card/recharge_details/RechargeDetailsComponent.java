package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description:
 */

@PerActivity
@Component(modules = RechargeDetailsPresenterModule.class, dependencies = AppComponent.class)
public interface RechargeDetailsComponent {

    void inject(RechargeDetailsActivity rechargeDetailsActivity);
}
