package com.zfsoftmh.ui.modules.chatting.contact;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactOperateNotifyListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.zfsoftmh.ui.modules.chatting.helper.FriendAddAcceptListener;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

/**
 * Created by sy
 * 2017/4/27.
 * <p>联系人操作事件</p>
 */
public class ContactOperateNotifyListenerImpl implements IYWContactOperateNotifyListener {

    //TODO 以下回调在UI线程回调 ，勿做太重的操作
    private FriendAddAcceptListener mListener;
    public void removeFriendAddAcceptListener(){
        mListener = null;
    }
    public void setFriendAddAcceptListener(FriendAddAcceptListener listener){
        mListener = listener;
    }


    /**
     * 用户请求加你为好友
     * @param contact 用户的信息
     * @param message 附带的备注
     */
    @Override
    public void onVerifyAddRequest(IYWContact contact, String message) {

    }

    /**
     * 用户接受了你的好友请求
     * @param contact 用户的信息
     */
    @Override
    public void onAcceptVerifyRequest(IYWContact contact) {
        IMKitHelper.getInstance().getIMKit().getContactService().syncContacts(null);
        if (mListener!=null) mListener.onAccept(contact);
    }

    /**
     * 用户拒绝了你的好友请求
     * @param contact 用户的信息
     */
    @Override
    public void onDenyVerifyRequest(IYWContact contact) {
        if (mListener!=null) mListener.onRefuse();
    }

    /**
     * 云旺服务端（或其它终端）进行了好友添加操作
     * @param contact 用户的信息
     */
    @Override
    public void onSyncAddOKNotify(IYWContact contact) {
        IMKitHelper.getInstance().getIMKit().getContactService().syncContacts(null);
    }

    /**
     * 用户从好友名单删除了您
     * @param contact 用户的信息
     */
    @Override
    public void onDeleteOKNotify(IYWContact contact) {
        IMKitHelper.getInstance().getIMKit().getContactService().syncContacts(null);
        YWIMKit mIMKit = IMKitHelper.getInstance().getIMKit();
        YWConversation conversation = mIMKit.getConversationService()
                .getConversationByUserId(contact.getUserId(), IMKitHelper.APP_KEY);
        IYWConversationService service = mIMKit.getConversationService();
        service.deleteConversation(conversation);
    }

    /**
     * 用户添加您为好友了
     *
     * @param contact 用户的信息
     */
    @Override
    public void onNotifyAddOK(IYWContact contact) {
        IMKitHelper.getInstance().getIMKit().getContactService().syncContacts(null);
    }

}
