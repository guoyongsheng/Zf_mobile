package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description:
 */

@PerActivity
@Component(modules = NoteDetailPresenterModule.class, dependencies = AppComponent.class)
interface NoteDetailComponent {
    void inject(NoteDetailActivity activity);
}
