package com.zfsoftmh.ui.modules.chatting.custom;

import android.content.Context;
import android.content.Intent;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMTribeAtPageOperation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.zfsoftmh.ui.modules.chatting.ZChatActivity;

/**
 * Created by sy
 * on 2017/4/27.
 */
public class AtMsgList_Operation extends IMTribeAtPageOperation {

    public AtMsgList_Operation(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public Class getChattingActivityClass() {
        return ZChatActivity.class;
    }

    @Override
    public Intent getStartChatActivityIntent(Context context, String appKey, String userId, YWMessage atMessage) {
        return super.getStartChatActivityIntent(context, appKey, userId, atMessage);
    }
}
