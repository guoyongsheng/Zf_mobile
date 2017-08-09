package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * 创建时间： 2017/5/4 0004
 * 编写人：
 * 功能描述：
 */

@PerActivity
@Component(modules = AddCloudNotePresenterModule.class, dependencies = AppComponent.class)
interface AddCloudNoteComponent {
    void inject(AddCloudNoteActivity addCloudNoteActivity);
}
