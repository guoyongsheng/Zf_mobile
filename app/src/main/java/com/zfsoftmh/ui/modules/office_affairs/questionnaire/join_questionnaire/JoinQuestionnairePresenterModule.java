package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description: 提供實例
 */

@Module
class JoinQuestionnairePresenterModule {

    private JoinQuestionnaireContract.View view;

    JoinQuestionnairePresenterModule(JoinQuestionnaireContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    JoinQuestionnairePresenter provideJoinQuestionnairePresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        return new JoinQuestionnairePresenter(view, officeAffairsApi, httpManager);
    }


    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
