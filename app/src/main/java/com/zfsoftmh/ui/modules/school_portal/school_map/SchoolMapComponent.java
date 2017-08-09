package com.zfsoftmh.ui.modules.school_portal.school_map;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by ljq on 2017/6/28.
 */

@PerActivity
@Component (modules = SchoolMapPresentModule.class,dependencies = AppComponent.class)
public interface SchoolMapComponent {
    void inject(SchoolMapActivity schoolMapActivity);
}
