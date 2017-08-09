package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

@PerActivity
@Component(modules = IntegralIncomePresenterModule.class, dependencies = AppComponent.class)
interface IntegralIncomeComponent {
    void inject(IntegralIncomeActivity activity);
}
