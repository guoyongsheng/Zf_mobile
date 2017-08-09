package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 提交问卷调查界面
 */

public class SubmitQuestionnaireActivity extends BaseActivity {

    private long id; //id

    private FragmentManager manager;
    private SubmitQuestionnaireFragment fragment;

    @Inject
    SubmitQuestionnairePresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        id = bundle.getLong("id", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.submit_quesionnaire);
        setDisplayHomeAsUpEnabled(true);

        fragment = (SubmitQuestionnaireFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SubmitQuestionnaireFragment.newInstance(id);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerSubmitQuestionnaireComponent.builder()
                .appComponent(getAppComponent())
                .submitQuestionnairePresenterModule(new SubmitQuestionnairePresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }
}
