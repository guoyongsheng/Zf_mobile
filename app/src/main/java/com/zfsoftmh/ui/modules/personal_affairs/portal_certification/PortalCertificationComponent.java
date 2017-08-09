package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = PortalCertificationPresenterModule.class, dependencies = AppComponent.class)
public interface PortalCertificationComponent {

    void inject(PortalCertificationActivity portalCertificationActivity);
}
