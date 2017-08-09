package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = DigitalFilePresenterModule.class, dependencies = AppComponent.class)
interface DigitalFileComponent {

    void inject(DigitalFileActivity digitalFileActivity);
}
