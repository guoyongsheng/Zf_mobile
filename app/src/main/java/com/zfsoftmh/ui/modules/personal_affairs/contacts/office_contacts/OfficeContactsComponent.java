package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = OfficeContactsPresenterModule.class, dependencies = AppComponent.class)
public interface OfficeContactsComponent {

    void inject(OfficeContactsActivity officeContactsActivity);
}
