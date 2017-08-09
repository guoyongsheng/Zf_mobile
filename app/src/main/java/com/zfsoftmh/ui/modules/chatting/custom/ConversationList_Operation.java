package com.zfsoftmh.ui.modules.chatting.custom;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListOperation;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWCustomConversationBody;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.helper.MsgType;

/**
 * Created by sy
 * on 2017/4/27.
 */
public class ConversationList_Operation extends IMConversationListOperation {

    public ConversationList_Operation(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public String getConversationHeadPath(Fragment fragment, YWConversation conversation) {
        return super.getConversationHeadPath(fragment, conversation);
    }

    @Override
    public int getConversationDefaultHead(Fragment fragment, YWConversation conversation) {
        return super.getConversationDefaultHead(fragment, conversation);
    }

    /**
     * 返回自定义会话的名称
     */
    @Override
    public String getConversationName(Fragment fragment, YWConversation conversation) {
        if (conversation.getConversationBody() instanceof YWCustomConversationBody) {
            YWCustomConversationBody body = (YWCustomConversationBody) conversation.getConversationBody();
            if (body.getIdentity().equals(MsgType.SYSTEM_TRIBE_CONVERSATION)) {
                return "群系统消息";
            } else if (body.getIdentity().equals(MsgType.SYSTEM_FRIEND_REQ_CONVERSATION)) {
                return "联系人系统消息";
            }
        }
        return null;
    }

    @Override
    public boolean onItemClick(Fragment fragment, YWConversation conversation) {
        if (conversation.getConversationType() == YWConversationType.Custom) {
            YWCustomConversationBody body = (YWCustomConversationBody) conversation.getConversationBody();
            String conversationId = body.getIdentity();
            if (conversationId.startsWith(MsgType.SYSTEM_FRIEND_REQ_CONVERSATION)) {
                IMKitHelper.getInstance().showSystemMessage(fragment.getActivity(),
                        MsgType.SYSTEM_FRIEND_REQ_CONVERSATION);
                return true;
            }else if (conversationId.startsWith(MsgType.SYSTEM_TRIBE_CONVERSATION)) {
                IMKitHelper.getInstance().showSystemMessage(fragment.getActivity(),
                        MsgType.SYSTEM_TRIBE_CONVERSATION);
                return true;
            }else if (conversationId.startsWith(MsgType.RELATED_ACCOUNT)){
                return false;
            }
            return false;
        }else{
            if(conversation.getConversationType() == YWConversationType.P2P){
                YWP2PConversationBody p2pBody = (YWP2PConversationBody) conversation.getConversationBody();
                String target = p2pBody.getContact().getUserId();
                IMKitHelper.getInstance().startChatting(fragment.getActivity(),target);
            }else if(conversation.getConversationType() == YWConversationType.Tribe){
                YWTribeConversationBody tribeBody = (YWTribeConversationBody) conversation.getConversationBody();
                long target = tribeBody.getTribe().getTribeId();
                IMKitHelper.getInstance().startTribeChatting(fragment.getActivity(),target);
            }
            return true;
        }
    }

    @Override
    public boolean onConversationItemLongClick(Fragment fragment, YWConversation conversation) {
        return super.onConversationItemLongClick(fragment, conversation);
    }
}
