package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-5-23
 * @Description: 我的日程列表界面
 */

public class ScheduleListActivity extends BaseActivity {

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
        setToolbarTitle(R.string.schedule_list);
        setDisplayHomeAsUpEnabled(true);

        ScheduleListFragment fragment = (ScheduleListFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ScheduleListFragment.newInstance();
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
