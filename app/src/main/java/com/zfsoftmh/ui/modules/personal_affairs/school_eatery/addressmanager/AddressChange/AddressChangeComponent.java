package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressChange;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerModule;

import dagger.Component;

/**
 * Created by li
 * on 2017/8/2.
 */
@PerActivity
@Component(modules = AddressManagerModule.class, dependencies = AppComponent.class)
public interface AddressChangeComponent {
    void inject(AddressChangeActvity addressChangeActvity);
}
