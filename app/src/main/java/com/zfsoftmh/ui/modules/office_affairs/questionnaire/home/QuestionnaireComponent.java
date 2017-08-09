package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published.PublishedPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published.UnPublishedPresenterModule;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = {PublishedPresenterModule.class, UnPublishedPresenterModule.class}, dependencies = AppComponent.class)
interface QuestionnaireComponent {

    void inject(QuestionnaireActivity questionnaireActivity);
}