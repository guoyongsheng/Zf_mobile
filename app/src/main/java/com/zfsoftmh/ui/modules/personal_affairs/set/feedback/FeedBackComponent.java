package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = FeedBackPresenterModule.class, dependencies = AppComponent.class)
interface FeedBackComponent {

    void inject(FeedBackActivity feedBackActivity);
}
