package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.QuestionnaireQuestionInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-8
 * @Description: 参与问卷调查界面
 */

public class JoinQuestionnaireActivity extends BaseActivity {

    private JoinQuestionnaireFragment fragment;
    private FragmentManager manager;

    private ArrayList<QuestionnaireQuestionInfo> list;

    @Inject
    JoinQuestionnairePresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.join_questionnaire);
        setDisplayHomeAsUpEnabled(true);

        fragment = (JoinQuestionnaireFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = JoinQuestionnaireFragment.newInstance(list);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerJoinQuestionnaireComponent.builder()
                .appComponent(getAppComponent())
                .joinQuestionnairePresenterModule(new JoinQuestionnairePresenterModule(fragment))
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
