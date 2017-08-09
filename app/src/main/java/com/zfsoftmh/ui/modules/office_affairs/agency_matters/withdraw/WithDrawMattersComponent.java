package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = WithDrawMattersPresenterModule.class, dependencies = AppComponent.class)
public interface WithDrawMattersComponent {

    void inject(WithDrawMattersActivity withDrawMattersActivity);
}
