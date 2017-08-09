package com.zfsoftmh.ui.modules.office_affairs.questionnaire.hit_the_title;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 打分题界面
 */

public class HitTheTitleActivity extends BaseActivity {

    private int type; // 0：添加 1: 编辑
    private long id;
    private FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        type = bundle.getInt("type", 0);
        id = bundle.getLong("id", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.hit_the_title);
        setDisplayHomeAsUpEnabled(true);

        HitTheTitleFragment fragment = (HitTheTitleFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = HitTheTitleFragment.newInstance(type, id);
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
