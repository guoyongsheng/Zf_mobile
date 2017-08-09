package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;
import dagger.Module;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description:
 */

@PerActivity
@Component(modules = IntegralGoodsDetailPresenterModule.class, dependencies = AppComponent.class)
interface IntegralGoodsDetailComponent {
    void inject(IntegralGoodsDetailActivity activity);
}
