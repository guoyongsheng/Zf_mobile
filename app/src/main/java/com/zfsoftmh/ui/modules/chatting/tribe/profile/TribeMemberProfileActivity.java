package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.TitleChangeInterface;
import com.zfsoftmh.ui.modules.chatting.contact.profile.ProFileFragment;

/**
 * Created by sy
 * on 2017/5/23.
 * <p>群成员个人信息</p>
 */
public class TribeMemberProfileActivity extends BaseActivity implements TitleChangeInterface {

    private FragmentManager fragmentManager;

    @Override
    protected void initVariables() {
        fragmentManager = getSupportFragmentManager();
        enableTouchToHideKeyboard();
    }

    private String targetID;
    private String nick;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        targetID = getIntent().getStringExtra("targetID");
        nick = getIntent().getStringExtra("targetNick");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ProFileFragment fragment = (ProFileFragment) fragmentManager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = ProFileFragment.newInstance(targetID,nick);
        }
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment,R.id.common_content);
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
