package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = SubmitQuestionnairePresenterModule.class, dependencies = AppComponent.class)
interface SubmitQuestionnaireComponent {

    void inject(SubmitQuestionnaireActivity submitQuestionnaireActivity);
}
