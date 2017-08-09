package com.zfsoftmh.ui.modules.common.login;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description:
 */

@PerActivity
@Component(modules = LoginPresenterModule.class, dependencies = AppComponent.class)
interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
