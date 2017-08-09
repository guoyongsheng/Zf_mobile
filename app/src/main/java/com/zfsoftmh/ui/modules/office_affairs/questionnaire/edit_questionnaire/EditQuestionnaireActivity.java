package com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 编辑问卷调查界面
 */
public class EditQuestionnaireActivity extends BaseActivity {

    private long id;
    private FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        id = bundle.getLong("id");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.edit_questionnaire);
        setDisplayHomeAsUpEnabled(true);

        EditQuestionnaireFragment fragment = (EditQuestionnaireFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = EditQuestionnaireFragment.newInstance(id);
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
