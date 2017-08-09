package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description:
 */

@PerActivity
@Component(modules = PersonalFilesPresenterModule.class, dependencies = AppComponent.class)
public interface PersonalFilesComponent {

    void inject(PersonalFilesActivity personalFilesActivity);
}
