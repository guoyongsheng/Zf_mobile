package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description:
 */

@PerActivity
@Component(modules = IntegralGoodsSearchPresenterModule.class, dependencies = AppComponent.class)
interface IntegralGoodsSearchComponent {
    void inject(IntegralGoodsSearchFragment fragment);
}
