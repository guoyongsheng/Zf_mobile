package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 提供实例
 */

@PerActivity
@Component(modules = DigitalFileDetailPresenterModule.class, dependencies = AppComponent.class)
interface DigitalFileDetailComponent {

    void inject(DigitalFileDetailFragment digitalFileDetailFragment);
}
