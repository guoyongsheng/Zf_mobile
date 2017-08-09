package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SearchPresenterModule.class, dependencies = AppComponent.class)
interface SearchComponent {

    void inject(SearchActivity searchActivity);
}
