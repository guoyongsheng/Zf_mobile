package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by ljq on 2017/7/25.
 */
@PerActivity
@Component(modules = EateryDetailModule.class,dependencies = AppComponent.class)
public interface EateryDetailComponent {
    void inject(EateryDetailActivity eateryDetailActivity);
}
