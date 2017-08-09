package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SubscriptionCenterPresenterModule.class, dependencies = AppComponent.class)
public interface SubscriptionCenterComponent {

    void inject(SubscriptionCenterActivity subscriptionCenterActivity);
}
