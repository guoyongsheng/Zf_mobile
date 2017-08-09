package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/7/14
 * @Description:
 */

@PerActivity
@Component(modules = ExchangeRecordDetailPresenterModule.class, dependencies = AppComponent.class)
interface ExchangeRecordDetailComponent {
    void inject(ExchangeRecordDetailActivity activity);
}
