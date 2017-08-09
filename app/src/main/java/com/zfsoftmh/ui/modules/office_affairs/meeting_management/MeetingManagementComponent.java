package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = MeetingManagementPresenterModule.class, dependencies = AppComponent.class)
interface MeetingManagementComponent {

    void inject(MeetingManagementActivity meetingManagementActivity);
}
