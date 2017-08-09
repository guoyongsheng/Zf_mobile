package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by li
 * on 2017/7/31.
 */
@PerActivity
@Component (modules = OrderSubmitModule.class,dependencies = AppComponent.class)
public interface OrderSubmitComponent {

    void inject(OrderSubmitActivity orderSubmitActivity);
}
