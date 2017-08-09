package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.OfficeAffairsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 提供实例
 */

@Module
class SubmitQuestionnairePresenterModule {

    private SubmitQuestionnaireContract.View view;

    SubmitQuestionnairePresenterModule(SubmitQuestionnaireContract.View view) {
        this.view = view;
    }


    @PerActivity
    @Provides
    SubmitQuestionnairePresenter provideSubmitQuestionnairePresenter(OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        return new SubmitQuestionnairePresenter(view, officeAffairsApi, httpManager);
    }

    @Provides
    OfficeAffairsApi provideOfficeAffairsApi(Retrofit retrofit) {
        return retrofit.create(OfficeAffairsApi.class);
    }
}
