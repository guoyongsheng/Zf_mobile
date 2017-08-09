package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description:
 */

@PerActivity
@Component(modules = EditNoteLabelPresenterModule.class,dependencies = AppComponent.class)
interface EditNoteLabelComponent {
    void inject(EditNoteLabelActivity activity);
}
