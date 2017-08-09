package com.zfsoftmh.ui.modules.chatting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.add.ContactSystemMessageFragment;
import com.zfsoftmh.ui.modules.chatting.helper.MsgType;
import com.zfsoftmh.ui.modules.chatting.tribe.add.TribeSystemMessageFragment;

/**
 * Created by sy
 * on 2017/5/8.
 * <p>系统消息</p>
 */

public class SystemMessageActivity extends BaseActivity {

    private String SysMsgType;
    @Override
    protected void initVariables() {  }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        SysMsgType = getIntent().getStringExtra("SysMsgType");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        if (SysMsgType.equals(MsgType.SYSTEM_FRIEND_REQ_CONVERSATION)){
            setToolbarTitle(R.string.sys_msg);
            setDisplayHomeAsUpEnabled(true);
            ContactSystemMessageFragment fragment =
                    (ContactSystemMessageFragment) manager.findFragmentById(R.id.common_content);
            if(fragment == null){
                fragment = ContactSystemMessageFragment.newInstance();
                ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
            }
        }else if(SysMsgType.equals(MsgType.SYSTEM_TRIBE_CONVERSATION)){
            setToolbarTitle("群系统消息");
            setDisplayHomeAsUpEnabled(true);
            TribeSystemMessageFragment fragment =
                    (TribeSystemMessageFragment) manager.findFragmentById(R.id.common_content);
            if(fragment == null){
                fragment = TribeSystemMessageFragment.newInstance();
                ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
            }
        }
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void initListener() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
