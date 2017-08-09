package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * 创建时间： 2017/5/11 0011
 * 编写人：
 * 功能描述：
 */

@PerActivity
@Component(modules = NoteLabelPresenterModule.class, dependencies = AppComponent.class)
interface NoteLabelComponent {
    void inject(NoteLabelActivity activity);
}
