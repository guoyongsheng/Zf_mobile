package com.zfsoftmh.ui.modules.chatting.custom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingBizService;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.channel.IMChannel;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.alibaba.mobileim.kit.chat.presenter.ChattingDetailPresenter;
import com.alibaba.wxlib.util.IMPrefsTools;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.chatting.contact.profile.ContactSetActivity;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.helper.KVStoreHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys;
import com.zfsoftmh.ui.modules.chatting.tribe.profile.TribeInfoActivity;

import java.util.List;

/**
 * Created by sy
 * on 2017/4/27.
 */
public class Chatting_UICustom extends IMChattingPageUI {

    public Chatting_UICustom(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public boolean needHideTitleView(Fragment fragment, YWConversation conversation) {
        return false;
    }

    private IMChattingBizService chattingBizService;

    @Override
    public void onInitFinished(final IMChattingBizService bizService){
        chattingBizService = bizService;
    }

    @Override
    public View getCustomTitleView(final Fragment fragment, final Context context, LayoutInflater inflater, final YWConversation conversation) {
        View view = inflater.inflate(R.layout.custom_chatting_title, new RelativeLayout(context), false);
        TextView textView = (TextView) view.findViewById(R.id.chatting_title);
        String title = null;
        if (conversation.getConversationType() == YWConversationType.P2P){
            YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation.getConversationBody();
            if (!TextUtils.isEmpty(conversationBody.getContact().getShowName())) {
                title = conversationBody.getContact().getShowName();
            } else {
                YWIMKit imKit = IMKitHelper.getInstance().getIMKit();
                IYWContact contact = imKit.getContactService().getContactProfileInfo(conversationBody.getContact().getUserId(), conversationBody.getContact().getAppKey());
                if (contact != null && !TextUtils.isEmpty(contact.getShowName())) {
                    title = contact.getShowName();
                }
            }
            if (TextUtils.isEmpty(title)) {
                title = conversationBody.getContact().getUserId();
            }
        }else{
            if(conversation.getConversationBody() instanceof YWTribeConversationBody){
                title = ((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeName();
                if (TextUtils.isEmpty(title)) {
                    title = "群会话";
                }
            }
        }
        textView.setText(title);
        TextView backView = (TextView) view.findViewById(R.id.back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragment.getActivity().finish();
            }
        });
        ImageView btn = (ImageView) view.findViewById(R.id.chatting_title_button);
        if (conversation.getConversationType() == YWConversationType.Tribe){
            btn.setImageResource(R.drawable.aliwx_tribe_info_icon);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到群设置
                    String conversationId = conversation.getConversationId();
                    long tribeId = Long.parseLong(conversationId.substring(5));
                    Intent intent = new Intent(fragment.getActivity(), TribeInfoActivity.class);
                    intent.putExtra(TribeKeys.TRIBE_ID, tribeId);
                    fragment.getActivity().startActivity(intent);
                }
            });
            btn.setVisibility(View.VISIBLE);
        } else if (conversation.getConversationType() == YWConversationType.P2P) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到单聊设置
                    YWP2PConversationBody pConversationBody = (YWP2PConversationBody) conversation.getConversationBody();
                    String userId = pConversationBody.getContact().getUserId();
                    Intent intent = new Intent(context, ContactSetActivity.class);
                    intent.putExtra("set_id",userId);
                    context.startActivity(intent);
                }
            });
            btn.setVisibility(View.VISIBLE);
            String feedbackAccount = IMPrefsTools.getStringPrefs(IMChannel.getApplication(), IMPrefsTools.FEEDBACK_ACCOUNT, "");
            if (!TextUtils.isEmpty(feedbackAccount) && feedbackAccount.equals(AccountUtils.getShortUserID(conversation.getConversationId()))) {
                btn.setVisibility(View.GONE);
            }
        }
        //群会话则显示@图标
        if (YWSDK_GlobalConfig.getInstance().enableTheTribeAtRelatedCharacteristic()) {
            if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                View atView = view.findViewById(R.id.aliwx_at_content);
                atView.setVisibility(View.VISIBLE);
                atView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = chattingBizService.getIMKit().getAtMsgListActivityIntent(context, conversation);
                        context.startActivity(intent);
                    }
                });
            }
        }
        return view;
    }

    /**
     * 定制图片预览页面titlebar右侧按钮点击事件
     *
     * @param message  当前显示的图片对应的ywmessage对象
     * @return true：使用用户定制的点击事件， false：使用默认的点击事件(默认点击事件为保持该图片到本地)
     */
    @Override
    public boolean onImagePreviewTitleButtonClick(Fragment fragment, YWMessage message) {
        return false;
    }

    /**
     * 返回图片保存的目录
     * @return 如果为null, 使用SDK默认的目录, 否则使用用户设置的目录
     */
    @Override
    public String getImageSavePath(Fragment fragment, YWMessage message) {
        return null;
    }

    /**
     * 返回单聊默认头像资源Id
     * @return 0:使用SDK默认提供的
     */
    @Override
    public int getDefaultHeadImageResId() {
        return 0;
    }

    /**
     * 聊天界面顶部展示的自定义View,这里的具体场景是当群消息屏蔽时展示的提示条
     *
     * @param fragment 聊天界面的Fragment
     * @param intent   打开聊天界面Activity的Intent
     * @return 返回要展示的View
     */
    @Override
    public View getChattingFragmentCustomViewAdvice(Fragment fragment, Intent intent) {
        return null;
    }
    /**
     * 是否需要在聊天界面展示顶部自定义View
     *
     * @param fragment 聊天界面的Fragment
     * @param intent   打开聊天界面Activity的Intent
     */
    @Override
    public boolean isUseChattingCustomViewAdvice(Fragment fragment, Intent intent) {
        return false;
    }
    /**
     * 聊天窗口消息item的头像上侧是否需要显示消息发送者昵称
     * @param conversation 当前聊天窗口所在会话
     * @param self         是否是自己发送的消息
     * @return true：显示昵称  false：不显示昵称
     */
    @Override
    public boolean needShowName(YWConversation conversation, boolean self) {
        return !self && KVStoreHelper.nickShow();
    }

    /**
     * 设置底部工具栏与键盘对齐
     */
    @Override
    public boolean needAlignReplyBar() {
        return true;
    }

    //生命周期 和chatFragment相同
    private boolean mIsMyComputerConv;

    @Override
    public void onStart(Fragment f, Intent intent, ChattingDetailPresenter presenter) {
        super.onStart(f, intent, presenter);
        mIsMyComputerConv = intent.getBooleanExtra(ChattingDetailPresenter.EXTRA_MYCOMPUTER, false);
    }

    public boolean isMyComputerConv() {
        return mIsMyComputerConv;
    }

    @Override
    public List<String> getMenuList(IYWContact loginAccount, YWMessage message) {
        return null;
    }

}
