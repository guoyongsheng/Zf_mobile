package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description:
 */

@PerActivity
@Component(modules = CloudNoteListPresenterModule.class, dependencies = AppComponent.class)
interface CloudNoteListComponent {
    void inject(CloudNoteListActivity activity);
}
