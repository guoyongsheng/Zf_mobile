package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017-5-23
 * @Description: 日程详情界面
 */

public class ScheduleDetailActivity extends BaseActivity {

    private long id; //id
    private int from; // 1: 是从通知点进来的
    private FragmentManager manager;
    private ScheduleDetailFragment fragment;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        id = bundle.getLong("id");
        from = bundle.getInt("from", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.schedule_management_detail);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ScheduleDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ScheduleDetailFragment.newInstance(id, from);
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
        dealWithBack();
    }

    @Override
    public void onBackPressed() {
        dealWithBack();
    }

    //处理返回键
    private void dealWithBack() {
        fragment.dealWithBack();
    }
}
