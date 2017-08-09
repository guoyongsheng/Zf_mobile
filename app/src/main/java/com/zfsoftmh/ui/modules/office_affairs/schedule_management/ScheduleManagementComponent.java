package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = ScheduleManagementPresenterModule.class, dependencies = AppComponent.class)
interface ScheduleManagementComponent {

    void inject(ScheduleManagementActivity scheduleManagementActivity);
}
