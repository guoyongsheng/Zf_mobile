package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted.HasBeenAcceptedPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted.NotAcceptedPresenterModule;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = {HasBeenAcceptedPresenterModule.class, NotAcceptedPresenterModule.class}, dependencies = AppComponent.class)
interface LostAndFoundComponent {

    void inject(LostAndFoundActivity lostAndFoundActivity);
}
