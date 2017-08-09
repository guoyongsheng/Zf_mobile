package com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 预览问卷界面
 */

public class PreviewQuestionnaireActivity extends BaseActivity {

    private long id; //id
    private FragmentManager manager;

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
        setToolbarTitle(R.string.preview_questionnaire);
        setDisplayHomeAsUpEnabled(true);

        PreviewQuestionnaireFragment fragment = (PreviewQuestionnaireFragment)
                manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = PreviewQuestionnaireFragment.newInstance(id);
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
