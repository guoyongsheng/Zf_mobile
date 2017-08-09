package com.zfsoftmh.di;

import android.content.Context;

import com.google.gson.Gson;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.common.utils.RealmHelper;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description:
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /**
     * 获取上下文对象
     *
     * @return context
     */
    Context getContext();

    /**
     * 获取Gson对象
     *
     * @return Gson实例
     */
    Gson getGson();

    /**
     * 获取Retrofit对象
     *
     * @return Retrofit实例
     */
    Retrofit getRetrofit();

    /**
     * 获取Retrofit对象
     *
     * @return Retrofit实例
     */
    @ForNoEncodeRetrofit
    Retrofit getRetrofitForLogin();

    /**
     * 获取HttpHelper
     *
     * @return HttpHelper的实例
     */
    HttpManager getHttpHelper();

    /**
     * 获取RealmHelper
     *
     * @return RealmHelper的实例
     */
    RealmHelper getRealmHelper();
}
