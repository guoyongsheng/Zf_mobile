package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description:
 */

@PerActivity
@Component(modules = FavouritesListPresenterModule.class, dependencies = AppComponent.class)
interface FavouritesListComponent {
    void inject(FavouritesListActivity activity);
}
