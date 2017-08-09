package com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SchoolDynamicsPresenterModule.class, dependencies = AppComponent.class)
interface SchoolDynamicsComponent {

    void inject(SchoolDynamicsFragment schoolDynamicsFragment);
}
