package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description:
 */

@PerActivity
@Component(modules = IntegralRankingPresenterModule.class, dependencies = AppComponent.class)
interface IntegralRankingComponent {
    void inject(IntegralRankingActivity activity);
}
