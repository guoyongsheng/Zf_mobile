package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = JoinQuestionnairePresenterModule.class, dependencies = AppComponent.class)
interface JoinQuestionnaireComponent {

    void inject(JoinQuestionnaireActivity joinQuestionnaireActivity);
}
