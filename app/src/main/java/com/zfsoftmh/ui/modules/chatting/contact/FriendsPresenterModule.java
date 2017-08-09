package com.zfsoftmh.ui.modules.chatting.contact;

import com.zfsoftmh.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sy
 * on 2017/5/2.
 */
@Module
public class FriendsPresenterModule {

    private FriendsContract.View view;

    public FriendsPresenterModule(FriendsContract.View view){
        this.view = view;
    }

    @PerActivity
    @Provides
    FriendsPresenter provideFriendsPresenter(){
        return new FriendsPresenter(view);
    }

}
