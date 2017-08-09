package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description:
 */

@PerActivity
@Component(modules = SuggestionsPresenterModule.class,dependencies = AppComponent.class)
interface SuggestionsComponent {
    void inject(SuggestionsActivity activity);
}
