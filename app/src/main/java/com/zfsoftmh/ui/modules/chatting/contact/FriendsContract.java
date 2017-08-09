package com.zfsoftmh.ui.modules.chatting.contact;

import android.content.Context;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/2.
 */
public interface FriendsContract {

    interface View extends BaseView<FriendsPresenter>{
        void loading(int pro);
        void stopLoading();
        void showErrorMsg(String errorMsg);
        void onGetContactsSuccess(ArrayList<ChatContact> localContactList);
        void onContactChange();
    }

    interface Presenter extends BasePresenter {
        void IOGetContacts();
        void showSwitch(Context context, ChatContact contact);
        void detach();
    }

}
