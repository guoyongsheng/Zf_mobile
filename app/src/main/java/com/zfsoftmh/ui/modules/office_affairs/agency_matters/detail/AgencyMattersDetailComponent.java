package com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = AgencyMattersDetailPresenterModule.class, dependencies = AppComponent.class)
public interface AgencyMattersDetailComponent {

    void inject(AgencyMattersDetailActivity agencyMattersDetailActivity);
}
