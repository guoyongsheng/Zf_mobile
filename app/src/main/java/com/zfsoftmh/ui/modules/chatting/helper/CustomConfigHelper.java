package com.zfsoftmh.ui.modules.chatting.helper;

import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.zfsoftmh.ui.modules.chatting.custom.AtMsgListUI_UICustom;
import com.zfsoftmh.ui.modules.chatting.custom.AtMsgList_Operation;
import com.zfsoftmh.ui.modules.chatting.custom.Chatting_Operation;
import com.zfsoftmh.ui.modules.chatting.custom.Chatting_UICustom;
import com.zfsoftmh.ui.modules.chatting.custom.ConversationList_Operation;
import com.zfsoftmh.ui.modules.chatting.custom.ConversationList_UICustom;
import com.zfsoftmh.ui.modules.chatting.custom.SelectTribeAtMember_UICustom;
import com.zfsoftmh.ui.modules.chatting.custom.SendAtMsgDetail_UICustom;
import com.zfsoftmh.ui.modules.chatting.custom.YWSDK_GlobalConfig;

/**
 * Created by sy
 * 2017/4/27.
 */
public class CustomConfigHelper {

    public static void initCustomConfig(){
        //聊天界面
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, Chatting_UICustom.class);
        //聊天业务
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_OPERATION_POINTCUT, Chatting_Operation.class);
        //会话列表界面
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationList_UICustom.class);
        //会话列表业务
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_OPERATION_POINTCUT, ConversationList_Operation.class);
        //消息通知栏
        AdviceBinder.bindAdvice(PointCutEnum.NOTIFICATION_POINTCUT, NotificationInitHelper.class);
        //全局配置修改
        AdviceBinder.bindAdvice(PointCutEnum.YWSDK_GLOBAL_CONFIG_POINTCUT, YWSDK_GlobalConfig.class);

        //@
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_FRAGMENT_AT_MSG_DETAIL, SendAtMsgDetail_UICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_AT_MSG_LIST, AtMsgListUI_UICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_AT_MSG_LIST_OP, AtMsgList_Operation.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_SELECT_AT_MEMBER, SelectTribeAtMember_UICustom.class);

    }

}
