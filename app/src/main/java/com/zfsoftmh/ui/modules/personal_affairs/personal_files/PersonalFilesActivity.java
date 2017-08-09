package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 个人档案界面
 */

public class PersonalFilesActivity extends BaseActivity {

    private FragmentManager manager;
    private PersonalFilesFragment fragment;

    @Inject
    PersonalFilesPresenter presenter;

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
        setToolbarTitle(R.string.mine_fragment_text_personal);
        setDisplayHomeAsUpEnabled(true);

        fragment = (PersonalFilesFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = PersonalFilesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerPersonalFilesComponent.builder()
                .appComponent(getAppComponent())
                .personalFilesPresenterModule(new PersonalFilesPresenterModule(fragment))
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
