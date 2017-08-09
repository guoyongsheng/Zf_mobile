package com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = BorrowInformationPresenterModule.class, dependencies = AppComponent.class)
interface BorrowInformationComponent {

    void inject(BorrowInformationActivity borrowInformationActivity);
}
