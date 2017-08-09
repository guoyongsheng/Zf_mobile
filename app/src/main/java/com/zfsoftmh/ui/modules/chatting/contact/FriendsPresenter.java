package com.zfsoftmh.ui.modules.chatting.contact;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.contact.IYWCrossContactProfileCallback;
import com.alibaba.mobileim.contact.IYWDBContact;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/2.
 */
public class FriendsPresenter implements FriendsContract.Presenter{

    private FriendsContract.View mView;
    private YWIMKit mIMKit;

    public FriendsPresenter(FriendsContract.View v){
        this.mView = v;
        mView.setPresenter(this);
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    IYWContactService getContactService() {
        return mIMKit.getContactService();
    }

    public YWIMKit getIMKit(){
        return mIMKit;
    }

    @Override
    public void cancelRequest() {

    }

    private ArrayList<ChatContact> cacheIOOrDBIOGetContacts() {
        ArrayList<ChatContact> mContacts = new ArrayList<>();
        List<IYWDBContact> contactsFromCache = getContactService().getContactsFromCache();
        IYWCrossContactProfileCallback callback = getContactService().getCrossContactProfileCallback();
        for (IYWDBContact aContactsFromCache : contactsFromCache) {
            IYWContact iywContact = callback.onFetchContactInfo(aContactsFromCache.getUserId(),aContactsFromCache.getAppKey());
            if (iywContact != null){
                ChatContact contact = new ChatContact(
                        iywContact.getUserId(),
                        iywContact.getAppKey(),
                        iywContact.getAvatarPath(),
                        iywContact.getShowName());
                contact.generateSpell();
                mContacts.add(contact);
            }
        }
        return mContacts;
    }

    private boolean hasLoaded;//已经加载过一次的标志
    @Override
    public void IOGetContacts() {
        ArrayList<ChatContact> localContacts = cacheIOOrDBIOGetContacts();
        if(localContacts.size() == 0 && !hasLoaded){
            netIOGetContacts();
        }else{
            mView.onGetContactsSuccess(localContacts);
        }
    }

    void netIOGetContacts() {
        hasLoaded = true;
        this.getContactService().syncContacts(new IWxCallback() {
            public void onSuccess(Object... result) {
                ArrayList<ChatContact> localContactList = cacheIOOrDBIOGetContacts();
                mView.onGetContactsSuccess(localContactList);
                mView.stopLoading();
            }

            public void onError(int code, String info) {
                mView.showErrorMsg("同步联系人失败");
                mView.stopLoading();
            }

            public void onProgress(int progress) {
                mView.loading(progress);
            }
        });

    }


    @Override
    public void showSwitch(final Context context, final ChatContact contact) {
        final IYWContactService contactService = mIMKit.getContactService();
        final String[] items = new String[]{"删除好友"};
        new YWAlertDialog.Builder(context)
                .setTitle(TextUtils.isEmpty(contact.getShowName())?contact.getUserId():contact.getShowName())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                deleteContact(contactService, contact, context);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    @Override
    public void detach() {
        mView = null;
    }

    private void deleteContact(IYWContactService contactService, ChatContact contact, final Context context) {
        contactService.delContact(contact.getUserId(), contact.getAppKey(), new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                IYWContact iywContact = (IYWContact) result[0];
                YWConversation conversation = mIMKit.getConversationService()
                        .getConversationByUserId(iywContact.getUserId(), IMKitHelper.APP_KEY);
                IYWConversationService service = mIMKit.getConversationService();
                service.deleteConversation(conversation);
                IMNotificationUtils.getInstance().showToast(context, "删除好友成功");
                IOGetContacts();
            }
            @Override
            public void onError(int code, String info) {
                IMNotificationUtils.getInstance().showToast(context, "删除好友失败");
            }
            @Override
            public void onProgress(int progress) {

            }
        });
    }


}
