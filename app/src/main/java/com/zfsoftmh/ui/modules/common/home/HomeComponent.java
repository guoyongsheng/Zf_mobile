package com.zfsoftmh.ui.modules.common.home;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.common.home.contacts.ContactsPresenterModule;
import com.zfsoftmh.ui.modules.common.home.find.FindPresenterModule;
import com.zfsoftmh.ui.modules.common.home.home.HomePresenterModule;
import com.zfsoftmh.ui.modules.common.home.mine.MinePresenterModule;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = {HomePresenterModule.class, FindPresenterModule.class,
        ContactsPresenterModule.class, MinePresenterModule.class}, dependencies = AppComponent.class)
interface HomeComponent {

    void inject(HomeActivity homeActivity);
}
