package com.zfsoftmh.ui.modules.chatting.helper.view.forward;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.google.gson.Gson;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.custom.Chatting_Operation;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.add.MemberSelectFragment;
import com.zfsoftmh.ui.modules.chatting.tribe.add.OnMemberSelectCallback;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/24.
 * <p>发送名片</p>
 */

public class CardSendActivity extends BaseActivity implements OnMemberSelectCallback {

    private FragmentManager fragmentManager;
    private YWIMKit mIMKit;
    @Override
    protected void initVariables() {
        fragmentManager = getSupportFragmentManager();
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    private YWConversationType conversationType;
    private YWConversation mConversation;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        String conversationID = getIntent().getStringExtra("conversation_id");
        mConversation = mIMKit.getConversationService().getConversationByConversationId(conversationID);
        if (mConversation == null){
            this.finish();
            IMNotificationUtils.getInstance().showToast(this, "出错了,请稍后再试");
        }
        conversationType = mConversation.getConversationType();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("选择名片");
        setDisplayHomeAsUpEnabled(true);
        MemberSelectFragment fragment = (MemberSelectFragment) fragmentManager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = MemberSelectFragment.newInstance(null);
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    @Override
    protected void initListener() { }

    @Override
    public void OnMemberSelect(ArrayList<ChatContact> selectData) {
        Gson gson = new Gson();
        for (ChatContact contact: selectData) {
            YWCustomMessageBody messageBody = new YWCustomMessageBody();
            CustomMsg msg = new CustomMsg();
            msg.customizeMessageType = Chatting_Operation.CustomMessageType.CARD;
            msg.arg0 = contact.getUserId();
            msg.arg1 = contact.getShowName();
            String data = gson.toJson(msg);
            messageBody.setContent(data);
            messageBody.setSummary("[名片]");
            YWMessage message = null;
            if(conversationType == YWConversationType.P2P){
                message = YWMessageChannel.createCustomMessage(messageBody);
            }else if(conversationType == YWConversationType.Tribe){
                message = YWMessageChannel.createTribeCustomMessage(messageBody);
            }
            if (message != null && mConversation != null)
                mConversation.getMessageSender().sendMessage(message, 120, null);
        }
        finish();
    }

}
