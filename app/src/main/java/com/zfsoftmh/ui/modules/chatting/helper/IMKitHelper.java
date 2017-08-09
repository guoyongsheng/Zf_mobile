package com.zfsoftmh.ui.modules.chatting.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.IYWP2PPushListener;
import com.alibaba.mobileim.IYWTribePushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactHeadClickListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.fundamental.model.YWIMSmiley;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.ui.chat.widget.YWSmilyMgr;
import com.alibaba.mobileim.utility.IMAutoLoginInfoStoreUtil;
import com.alibaba.wxlib.util.SysUtil;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.ui.modules.chatting.SystemMessageActivity;
import com.zfsoftmh.ui.modules.chatting.ZChatActivity;
import com.zfsoftmh.ui.modules.chatting.contact.ContactCacheUpdateListenerImpl;
import com.zfsoftmh.ui.modules.chatting.contact.ContactOperateNotifyListenerImpl;
import com.zfsoftmh.ui.modules.chatting.custom.Chatting_Operation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by sy
 * on 2017/4/27
 */
public class IMKitHelper {

    public static String APP_KEY = Config.SCHOOL.CHATTING_ID;

    private static IMKitHelper mInstance = new IMKitHelper();
    public static IMKitHelper getInstance(){
        return mInstance;
    }

    private YWIMKit mIMKit;

    public void initIMKit(String userId){
        mIMKit = YWAPI.getIMKitInstance(userId, APP_KEY);
        //消息监听
        addPushMessageListener();
        //联系人监听
        addContactListeners();

//        addContactHeadClickListener();
    }

    public YWIMKit getIMKit() {
        return mIMKit;
    }

    public void initSDK(Application context){
        String appKey = IMAutoLoginInfoStoreUtil.getAppkey();
        if(TextUtils.isEmpty(appKey)){
            YWAPI.init(context, APP_KEY);
        }else{
            YWAPI.init(context, appKey);
        }
        NotificationInitHelper.init();
        //添加自定义表情的初始化
        YWSmilyMgr.setSmilyInitNotify(new YWSmilyMgr.SmilyInitNotify() {
            @Override
            public void onDefaultSmilyInitOk() {
                //隐藏默认表情，必须在添加前调用
//                SmilyCustomSample.hideDefaultSmiley();
//                SmilyCustomSample.addDefaultSmiley();
                //如果想修改顺序（把sdk默认的放在自己添加的表情后面），可以先hide默认的然后再最后添加默认的
                SmilyCustomHelper.addNewEmojiSmiley();
                YWIMSmiley smiley = SmilyCustomHelper.addNewImageSmiley();
                smiley.setIndicatorIconResId(R.drawable.aliwx_s012);
                SmilyCustomHelper.setSmileySize(YWIMSmiley.SMILEY_TYPE_IMAGE, 60);
                //最后要清空通知，防止memory leak
                YWSmilyMgr.setSmilyInitNotify(null);
            }
        });
        YWAPI.enableSDKLogOutput(true);
    }

    private void addContactHeadClickListener(){
        if(mIMKit != null){
            mIMKit.getContactService().setContactHeadClickListener(new IYWContactHeadClickListener() {
                @Override
                public void onUserHeadClick(Fragment fragment, YWConversation ywConversation, String userId, String appKey, boolean isConversationListPage) {

                }

                @Override
                public void onTribeHeadClick(Fragment fragment, YWConversation ywConversation, long l) {

                }

                @Override
                public void onCustomHeadClick(Fragment fragment, YWConversation ywConversation) {

                }
            });
        }
    }

    public void initFeedBack(Application application) {
//        if(SysUtil.isMainProcess()) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("loginTime", "登录时间");
//                jsonObject.put("visitPath", "登陆，关于，反馈");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            FeedbackAPI.initFeedback(application, YWAPI.getAppKey(), "反馈", null);
//            FeedbackAPI.setAppExtInfo(jsonObject);
//
//            FeedbackAPI.setCustomContact("", false);
//        }
    }


    /**
     * 新消息到达监听，该监听应该在登录之前调用以保证登录后可以及时收到消息
     */
    private void addPushMessageListener(){
        if (mIMKit == null) {
            return;
        }
        IYWConversationService conversationService = mIMKit.getConversationService();
        //添加单聊消息监听，先删除再添加，以免多次添加该监听
        conversationService.removeP2PPushListener(mP2PListener);
        conversationService.addP2PPushListener(mP2PListener);

        //添加群聊消息监听，先删除再添加，以免多次添加该监听
        conversationService.removeTribePushListener(mTribeListener);
        conversationService.addTribePushListener(mTribeListener);
    }

    /**
     * 联系人相关操作通知回调，SDK使用方可以实现此接口来接收联系人操作通知的监听
     * 所有方法都在UI线程调用
     * SDK会自动处理这些事件，一般情况下，用户不需要监听这个事件
     */
    private void addContactListeners(){
        //添加联系人通知和更新监听，先删除再添加，以免多次添加该监听
        if(mIMKit!=null){
            mIMKit.getContactService().removeContactOperateNotifyListener(mContactOperateNotifyListener);
            mIMKit.getContactService().addContactOperateNotifyListener(mContactOperateNotifyListener);

            mIMKit.getContactService().removeContactCacheUpdateListener(mContactCacheUpdateListener);
            mIMKit.getContactService().addContactCacheUpdateListener(mContactCacheUpdateListener);
        }
    }

    private ContactCacheUpdateListenerImpl mContactCacheUpdateListener = new ContactCacheUpdateListenerImpl();
    private ContactOperateNotifyListenerImpl mContactOperateNotifyListener = new ContactOperateNotifyListenerImpl();

    public ContactOperateNotifyListenerImpl getContactOperateNotifyListener(){
        return mContactOperateNotifyListener;
    }

    private IYWP2PPushListener mP2PListener = new IYWP2PPushListener() {
        @Override
        public void onPushMessage(IYWContact contact, List<YWMessage> messages) {
            for (YWMessage message : messages) {
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS) {
                    if (message.getMessageBody() instanceof YWCustomMessageBody) {
                        YWCustomMessageBody messageBody = (YWCustomMessageBody) message.getMessageBody();
                        if (messageBody.getTransparentFlag() == 1) {
                            String content = messageBody.getContent();
                            try {
                                JSONObject object = new JSONObject(content);
                                if (object.has("text")) {
                                    String text = object.getString("text");
                                    //TODO text
                                } else if (object.has("customizeMessageType")) {
                                    String customType = object.getString("customizeMessageType");
                                    if (!TextUtils.isEmpty(customType) && customType.equals(Chatting_Operation.CustomMessageType.READ_STATUS)) {
                                        YWConversation conversation = mIMKit.getConversationService().getConversationByConversationId(message.getConversationId());
                                        long msgId = Long.parseLong(object.getString("PrivateImageRecvReadMessageId"));
                                        conversation.updateMessageReadStatus(conversation, msgId);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    };

    private IYWTribePushListener mTribeListener = new IYWTribePushListener() {
        @Override
        public void onPushMessage(YWTribe tribe, List<YWMessage> messages) {
            //TODO 收到群消息
        }
    };

    public void login(String userId,IWxCallback callback) {
        if (mIMKit == null) {
            return;
        }
        SysUtil.setCnTaobaoInit(true);
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userId, "654321");
        IYWLoginService mLoginService = mIMKit.getLoginService();
        mLoginService.login(loginParam, callback);
    }

    public void startChatting(Activity context, String target){
        Intent intent = new Intent(context, ZChatActivity.class);
        intent.putExtra("targetID",target);
        context.startActivity(intent);
    }

    public void startTribeChatting(Activity context, long target){
        Intent intent = new Intent(context, ZChatActivity.class);
        intent.putExtra("tribeID",target);
        context.startActivity(intent);
    }

    public void showSystemMessage(Activity context,String type){
        Intent intent = new Intent(context, SystemMessageActivity.class);
        intent.putExtra("SysMsgType",type);
        context.startActivity(intent);
    }

}
