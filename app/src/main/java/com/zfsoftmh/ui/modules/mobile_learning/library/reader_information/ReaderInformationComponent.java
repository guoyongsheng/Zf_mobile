package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = ReaderInformationPresenterModule.class, dependencies = AppComponent.class)
interface ReaderInformationComponent {

    void inject(ReaderInformationActivity readerInformationActivity);
}
