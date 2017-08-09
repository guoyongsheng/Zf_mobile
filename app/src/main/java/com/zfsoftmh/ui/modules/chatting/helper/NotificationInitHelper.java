package com.zfsoftmh.ui.modules.chatting.helper;

import android.content.Intent;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMNotification;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWCustomConversationBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.utility.IMSmilyCache;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.modules.chatting.SystemMessageActivity;

/**
 * Created by sy
 * 2017/4/27.
 * <p>通知栏的设置</p>
 */
public class NotificationInitHelper extends IMNotification {


	public NotificationInitHelper(Pointcut pointcut) {
		super(pointcut);
	}

	public static void init() {
		YWIMKit imKit = IMKitHelper.getInstance().getIMKit();
		if (imKit != null) {
			// 设置是否开启通知提醒
			imKit.setEnableNotification(true);

		}
	}

    /**
     * 是否开启免打扰模式，若开启免打扰模式则收到新消息时不发送通知栏提醒，只在会话列表页面显示未读数
     * 若开启免打扰模式，则声音提醒和震动提醒会失效，即收到消息时不会有震动和提示音
     * @param conversation 会话id
     * @param message 收到的消息
     * @return true:开启， false：不开启
     */
    @Override
    public boolean needQuiet(YWConversation conversation, YWMessage message) {
        return KVStoreHelper.getNeverDisturb();
    }

    /**
     * 收到通知栏消息时是否震动提醒，该设置在没有开启免打扰模式的情况下才有效
     * @param conversation 会话id
     * @param message 收到的消息
     * @return true：震动，false：不震动
     */
    @Override
    public boolean needVibrator(YWConversation conversation, YWMessage message) {
        return KVStoreHelper.getNeedVibration();
    }


    /**
     * 收到通知栏消息时是否有声音提醒，该设置在没有开启免打扰模式的情况下才有效
     * @param conversation 会话id
     * @param message 收到的消息
     * @return true：有提示音，false：没有提示音
     */
    @Override
    public boolean needSound(YWConversation conversation, YWMessage message) {
        return KVStoreHelper.getNeedSound();
    }

    /**
     * 收到消息时，自定义消息通知栏的提示文案
     */
    @Override
    public CharSequence getNotificationTips(YWConversation conversation, YWMessage message, int totalUnReadCount, IMSmilyCache smilyCache) {
        if (conversation.getConversationType() == YWConversationType.Custom) {
            YWCustomConversationBody body = (YWCustomConversationBody) conversation.getConversationBody();
            String conversationId = body.getIdentity();
            if (conversationId.startsWith(MsgType.SYSTEM_FRIEND_REQ_CONVERSATION)) {
                return "您有新的好友请求";
            }else if (conversationId.startsWith(MsgType.SYSTEM_TRIBE_CONVERSATION)) {
                return "您有新的群组请求";
            }else if (conversationId.startsWith(MsgType.RELATED_ACCOUNT)){

            }
        }
        return null;
    }

    /**
     * 收到消息时的自定义通知栏点击Intent
     * @param conversation  收到消息的会话
     * @param message  收到的消息
     * @param totalUnReadCount  会话中消息未读数
     * @return 如果返回null，则使用全局自定义Intent
     */
    public Intent getCustomNotificationIntent(YWConversation conversation, YWMessage message, int totalUnReadCount) {
        if (conversation.getConversationType() == YWConversationType.Custom) {
            YWCustomConversationBody body = (YWCustomConversationBody) conversation.getConversationBody();
            String conversationId = body.getIdentity();
            if (conversationId.startsWith(MsgType.SYSTEM_FRIEND_REQ_CONVERSATION)) {
                Intent intent = new Intent(BaseApplication.getContext(), SystemMessageActivity.class);
                intent.putExtra("SysMsgType",MsgType.SYSTEM_FRIEND_REQ_CONVERSATION);
                return intent;
            }else if (conversationId.startsWith(MsgType.SYSTEM_TRIBE_CONVERSATION)) {

            }else if (conversationId.startsWith(MsgType.RELATED_ACCOUNT)){

            }
        }
        return null;
    }

    /**
     * 获取通知栏图标Icon
     * @return ResId
     */
    @Override
    public int getNotificationIconResID() {
        return R.drawable.aliwx_notification_bg;
    }

    /**
     * 获取通知栏显示Title
     */
    @Override
    public String getAppName() {
        return "我的消息";
    }

    /**
     * 返回自定义提示音资源Id
     * @return  提示音资源Id，返回0则使用SDK默认的提示音
     */
    @Override
    public int getNotificationSoundResId() {
        return 0;
    }


    /**
     * 自定义通知栏ticker
     * @param conversation 收到消息的会话
     * @param message 收到的消息
     * @param totalUnReadCount 会话中消息未读数
     * @return 如果返回null，则使用SDK默认的ticker
     */
    @Override
    public String getTicker(YWConversation conversation, YWMessage message, int totalUnReadCount) {
        return null;
    }

}
