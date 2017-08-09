package com.zfsoftmh.ui.modules.chatting.contact.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.constant.YWProfileSettingsConstants;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.YWContactManager;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.kit.contact.ContactHeadLoadHelper;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;

/**
 * Created by sy
 * on 2017/5/12.
 */
public class ContactSetActivity extends BaseActivity{

    private String targetID;
    private YWContactManager contactManager;
    private IYWConversationService conversationService;
    private YWIMKit mIMKit;
    @Override
    protected void initVariables() {
        mIMKit = IMKitHelper.getInstance().getIMKit();
        contactManager = (YWContactManager) mIMKit.getContactService();
        conversationService = mIMKit.getConversationService();
    }

    private IYWContact contact;
    private YWConversation conversation;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        targetID = getIntent().getStringExtra("set_id");
        contact = contactManager.getContactProfileInfo(targetID, IMKitHelper.APP_KEY);
        conversation = conversationService.getConversationByUserId(targetID);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_contact_set;
    }

    private ToggleButton btnMsgNotify/*消息免打扰*/, btnMakeTop/*置顶*/;
    private RelativeLayout clearCache;
    private View parent;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("设置");
        setDisplayHomeAsUpEnabled(true);
        parent = findViewById(R.id.parent);
        ImageView header = (ImageView) findViewById(R.id.contact_set_header);
        TextView tvName = (TextView) findViewById(R.id.contact_set_name);
        btnMsgNotify = (ToggleButton) findViewById(R.id.btn_msg);
        btnMakeTop = (ToggleButton) findViewById(R.id.btn_top);
        clearCache = (RelativeLayout) findViewById(R.id.rl_clear_cache);
        //设置头像
        ContactHeadLoadHelper mContactHeadLoadHelper = new ContactHeadLoadHelper(this, null,
                mIMKit.getUserContext());
        mContactHeadLoadHelper.setHeadView(header, targetID, IMKitHelper.APP_KEY, true);
        //设置名字
        if (contact != null)  tvName.setText(contact.getShowName());
        else  tvName.setText(targetID);
        //检查消息免打扰是否打开
        int msgRecFlag = YWProfileSettingsConstants.RECEIVE_PEER_MSG;
        if(contactManager != null)
            msgRecFlag = contactManager.getMsgRecFlagForContact(targetID, IMKitHelper.APP_KEY);
        if(msgRecFlag == YWProfileSettingsConstants.RECEIVE_PEER_MSG_NOT_REMIND)
            btnMsgNotify.setChecked(true);
         else
            btnMsgNotify.setChecked(false);
        //检查是否指定
        boolean isTop = conversation.isTop();
        if (isTop)
            btnMakeTop.setChecked(true);
        else
            btnMakeTop.setChecked(false);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        btnMsgNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (contact == null)  return;
                if(isChecked)
                    contactManager.setContactMsgRecType(contact, YWProfileSettingsConstants.RECEIVE_PEER_MSG_NOT_REMIND, 10, null);
                else
                    contactManager.setContactMsgRecType(contact, YWProfileSettingsConstants.RECEIVE_PEER_MSG, 10, null);
            }
        });

        btnMakeTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mIMKit.getIMCore().getConversationService().setTopConversation(conversation);
                else
                    mIMKit.getIMCore().getConversationService().removeTopConversation(conversation);
            }
        });

        clearCache.setOnClickListener(clickListener);
    }

    /**
     * 单次的点击监听
     */
    private OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            if (v.getId() == R.id.rl_clear_cache){
                clearMsgRecord();
            }
        }
    };

    /**
     * 打开清除消息缓存对话框
     */
    protected void clearMsgRecord() {
        String message = "清空的消息再次漫游时不会出现\n你确定要清空聊天消息吗？";
        AlertDialog.Builder builder = new WxAlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                conversation.getMessageLoader().deleteAllMessage();
                                Snackbar.make(parent,"记录已清空",Snackbar.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        builder.create().show();
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
