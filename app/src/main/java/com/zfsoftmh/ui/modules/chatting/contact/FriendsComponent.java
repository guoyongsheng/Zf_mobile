package com.zfsoftmh.ui.modules.chatting.contact;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * Created by sy
 * on 2017/5/2.
 */

@PerActivity
@Component(modules = FriendsPresenterModule.class, dependencies = AppComponent.class)
public interface FriendsComponent {

    void inject(ChatContactActivity activity);
}
