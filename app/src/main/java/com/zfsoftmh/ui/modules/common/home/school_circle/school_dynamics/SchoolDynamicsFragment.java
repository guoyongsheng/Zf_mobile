package com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics;

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

public class SchoolDynamicsFragment extends BaseFragment<SchoolDynamicsPresenter> implements
        SchoolDynamicsContract.View {

    @Inject
    SchoolDynamicsPresenter presenter;

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

    }

    @Override
    protected void initListener() {

    }

    public static SchoolDynamicsFragment newInstance() {
        return new SchoolDynamicsFragment();
    }

    @Override
    public void inject() {
        DaggerSchoolDynamicsComponent.builder()
                .appComponent(getAppComponent())
                .schoolDynamicsPresenterModule(new SchoolDynamicsPresenterModule(this))
                .build()
                .inject(this);
    }
}
