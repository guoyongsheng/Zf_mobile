package com.zfsoftmh.ui.modules.chatting.tribe;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by sy
 * on 2017/5/12.
 */

@PerActivity
@Component(modules = TribePresenterModule.class, dependencies = AppComponent.class)
public interface TribeComponent {

    void inject(TribeActivity activity);
}
