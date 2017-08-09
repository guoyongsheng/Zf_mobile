package com.zfsoftmh.ui.modules.common.splash;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SplashPresenterModule.class, dependencies = AppComponent.class)
interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
