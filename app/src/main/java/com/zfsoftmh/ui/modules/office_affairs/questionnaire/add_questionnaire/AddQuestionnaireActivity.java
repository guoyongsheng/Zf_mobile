package com.zfsoftmh.ui.modules.office_affairs.questionnaire.add_questionnaire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 添加问卷调查界面
 */

public class AddQuestionnaireActivity extends BaseActivity {

    private FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.add_questionnaire);
        setDisplayHomeAsUpEnabled(true);

        AddQuestionnaireFragment fragment = (AddQuestionnaireFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = AddQuestionnaireFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {

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
