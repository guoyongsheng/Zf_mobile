package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = MyReleasedPresenterModule.class, dependencies = AppComponent.class)
interface MyReleasedComponent {

    void inject(MyReleasedActivity myReleasedActivity);
}
