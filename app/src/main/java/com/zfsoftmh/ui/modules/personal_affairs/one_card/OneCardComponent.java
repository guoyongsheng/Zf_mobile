package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = OneCardPresenterModule.class, dependencies = AppComponent.class)
public interface OneCardComponent {

    void inject(OneCardActivity oneCardActivity);
}
