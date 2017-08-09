package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

@PerActivity
@Component(modules = ExchangeRecordPresenterModule.class, dependencies = AppComponent.class)
interface ExchangeRecordComponent {
    void inject(ExchangeRecordFragment fragment);
}
