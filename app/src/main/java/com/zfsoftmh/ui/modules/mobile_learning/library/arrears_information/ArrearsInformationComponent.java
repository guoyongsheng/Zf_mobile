package com.zfsoftmh.ui.modules.mobile_learning.library.arrears_information;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = ArrearsInformationPresenterModule.class, dependencies = AppComponent.class)
interface ArrearsInformationComponent {

    void inject(ArrearsInformationActivity arrearsInformationActivity);
}
