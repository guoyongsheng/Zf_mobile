package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;

import java.util.List;

/**
 * Created by sy
 * on 2017/5/15.
 */

interface TribeInfoContract {

    interface View {
        void showErrMsg(String msg);
        void onProfileUpdate(YWTribe tribe);
        void updateMembersView(List<IYWContact> list);
        void onQuitTribe();
        void onTribeInfoModify(String newName,String newNick);
    }

    interface Presenter {
        void onDetach();
        void getTribeInfoFromServer();
        void initTribeInfo();
        String getLoginUserTribeNick();
        void setConversation(boolean isTop);
        void quitTribe();
        void modifyTribeInfo(String newData);
        void modifyTribeNick(String newNick);
        void setMsgNotify(boolean notify);
    }
}
