package com.zfsoftmh.ui.modules.common.home.school_circle.my_attention;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 提供实例
 */

@PerActivity
@Component(modules = MyAttentionPresenterModule.class, dependencies = AppComponent.class)
interface MyAttentionComponent {

    void inject(MyAttentionFragment myAttentionFragment);
}
