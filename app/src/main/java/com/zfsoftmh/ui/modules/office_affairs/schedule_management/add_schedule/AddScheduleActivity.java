package com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-5-23
 * @Description: 添加日程界面
 */

public class AddScheduleActivity extends BaseActivity {

    private FragmentManager manager;
    private boolean is_edit; //是否是编辑
    private long id;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        is_edit = bundle.getBoolean("is_edit", false);
        id = bundle.getLong("id");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.add_schedule);
        setDisplayHomeAsUpEnabled(true);

        AddScheduleFragment fragment = (AddScheduleFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = AddScheduleFragment.newInstance(is_edit, id);
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
