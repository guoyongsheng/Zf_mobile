package com.zfsoftmh.ui.modules.mobile_learning.library.booking_information;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.service.MobileLearningApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 提供实例
 */

@Module
class BookingInformationPresenterModule {

    private BookingInformationContract.View view;

    BookingInformationPresenterModule(BookingInformationContract.View view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    BookingInformationPresenter provideBookingInformationPresenter(MobileLearningApi mobileLearningApi,
            HttpManager httpManager) {

        return new BookingInformationPresenter(view, mobileLearningApi, httpManager);
    }


    @Provides
    MobileLearningApi provideMobileLearningApi(Retrofit retrofit) {
        return retrofit.create(MobileLearningApi.class);
    }
}
