package com.zfsoftmh.ui.modules.common.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContactActivity;
import com.zfsoftmh.ui.modules.chatting.helper.DefFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 消息ui
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private FragmentManager manager;
    private ImageButton btn_add; //添加
    private RelativeLayout relativeLayout;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        manager = getChildFragmentManager();
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initViews(View view) {

        btn_add = (ImageButton) view.findViewById(R.id.message_header_add);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.header_relative);

        Fragment conversationFragment;
        if (IMKitHelper.getInstance().getIMKit() != null &&
                IMKitHelper.getInstance().getIMKit().getIMCore().getWxAccount() != null) {
            btn_add.setVisibility(View.VISIBLE);
            conversationFragment = IMKitHelper.getInstance().getIMKit().getConversationFragment();
        } else {
            btn_add.setVisibility(View.GONE);
            conversationFragment = DefFragment.newInstance();
        }
        FragmentTransaction transition = manager.beginTransaction();
        transition.replace(R.id.common_content, conversationFragment);
        transition.commit();
    }

    @Override
    protected void initListener() {
        btn_add.setOnClickListener(this);
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ChatContactActivity.class);
        openActivity(intent);
    }


    @Override
    protected boolean immersionEnabled() {
        return true;
    }

    @Override
    protected void immersionInit() {
        super.immersionInit();

        if (immersionBar == null) {
            return;
        }
        immersionBar.statusBarColor(R.color.colorPrimary)
                .fitsSystemWindows(true)
                .init();
    }
}
