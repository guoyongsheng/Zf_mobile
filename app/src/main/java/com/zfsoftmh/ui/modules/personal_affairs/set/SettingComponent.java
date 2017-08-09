package com.zfsoftmh.ui.modules.personal_affairs.set;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SettingPresenterModule.class, dependencies = AppComponent.class)
public interface SettingComponent {

    void inject(SettingActivity settingActivity);
}
