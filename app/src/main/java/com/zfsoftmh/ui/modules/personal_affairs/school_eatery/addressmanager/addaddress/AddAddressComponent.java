package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/7/20.
 */


@PerActivity
@Component(modules = AddAddressModule.class,dependencies = AppComponent.class)
public interface AddAddressComponent {
   void inject(AddAddressActivity addAddressActivity);

}
