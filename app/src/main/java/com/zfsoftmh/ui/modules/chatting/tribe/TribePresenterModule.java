package com.zfsoftmh.ui.modules.chatting.tribe;

import com.zfsoftmh.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sy
 * on 2017/5/12.
 */
@Module
public class TribePresenterModule {

    private TribeContract.View mView;

    public TribePresenterModule(TribeContract.View view){
        this.mView = view;
    }

    @PerActivity
    @Provides
    TribePresenter provideTribePresenter(){
        return new TribePresenter(mView);
    }

}
