package com.zfsoftmh.ui.modules.office_affairs.agency_matters.submit;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/7
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SubmitMattersPresenterModule.class, dependencies = AppComponent.class)
public interface SubmitMattersComponent {

    void inject(SubmitMattersActivity submitMattersActivity);
}
