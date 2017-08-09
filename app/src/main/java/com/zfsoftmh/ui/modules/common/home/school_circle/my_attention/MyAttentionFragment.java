package com.zfsoftmh.ui.modules.common.home.school_circle.my_attention;

import android.os.Bundle;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: ui
 */

public class MyAttentionFragment extends BaseFragment<MyAttentionPresenter> implements MyAttentionContract.View {


    @Inject
    MyAttentionPresenter presenter;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {
        inject();
    }

    @Override
    protected void initListener() {

    }

    public static MyAttentionFragment newInstance() {
        return new MyAttentionFragment();
    }

    @Override
    public void inject() {

        DaggerMyAttentionComponent.builder()
                .appComponent(getAppComponent())
                .myAttentionPresenterModule(new MyAttentionPresenterModule(this))
                .build()
                .inject(this);
    }
}
