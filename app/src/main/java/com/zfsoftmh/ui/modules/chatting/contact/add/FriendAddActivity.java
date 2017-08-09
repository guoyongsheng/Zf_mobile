package com.zfsoftmh.ui.modules.chatting.contact.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.TitleChangeInterface;

/**
 * Created by sy
 * on 2017/5/4.
 * <p>添加好友</p>
 */
public class FriendAddActivity extends BaseActivity implements TitleChangeInterface{

    private FragmentManager manager;
    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
        enableTouchToHideKeyboard();
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
        FriendAddFragment fragment = (FriendAddFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = FriendAddFragment.newInstance();
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
    public void onFragmentActive(String title) {
        setToolbarTitle(title);
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
