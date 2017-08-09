package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by li
 * on 2017/8/2.
 */

@PerActivity
@Component(modules = OrderDetailModule.class,dependencies = AppComponent.class)
public interface OrderDetailComponent {
    void inject(OrderDetailActivity orderDetailActivity);
}
