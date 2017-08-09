package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

@PerActivity
@Component(modules = MyMessagePresenterModule.class, dependencies = AppComponent.class)
interface MyMessageComponent {
    void inject(MyMessageActivity activity);
}
