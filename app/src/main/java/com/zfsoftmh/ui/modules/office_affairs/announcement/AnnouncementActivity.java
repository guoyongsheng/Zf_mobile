package com.zfsoftmh.ui.modules.office_affairs.announcement;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 通知公告界面
 */

public class AnnouncementActivity extends BaseActivity {

    private AppCenterItemInfo itemInfo;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        itemInfo = bundle.getParcelable("info");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(itemInfo.getName());
        setDisplayHomeAsUpEnabled(true);
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
