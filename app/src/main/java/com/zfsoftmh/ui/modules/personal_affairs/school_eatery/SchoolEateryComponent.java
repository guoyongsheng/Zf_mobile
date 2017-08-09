package com.zfsoftmh.ui.modules.personal_affairs.school_eatery;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerModule;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.SchoolEateryModule;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.OrderFromModule;

import dagger.Component;

/**
 * Created by ljq on 2017/7/19.
 */
@PerActivity
@Component(modules = {AddressManagerModule.class, SchoolEateryModule.class, OrderFromModule.class},
        dependencies = AppComponent.class)
public interface SchoolEateryComponent {
    void inject(SchoolEateryActivity schoolEateryActivity);
}
