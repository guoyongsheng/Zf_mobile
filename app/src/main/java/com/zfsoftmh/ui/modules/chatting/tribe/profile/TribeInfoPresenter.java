package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeMember;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeRole;
import com.alibaba.mobileim.gingko.presenter.contact.IContactProfileUpdateListener;
import com.alibaba.mobileim.gingko.presenter.tribe.IYWTribeChangeListener;
import com.alibaba.mobileim.tribe.IYWTribeService;
import com.alibaba.mobileim.utility.IMConstants;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sy
 * on 2017/5/15.
 */
class TribeInfoPresenter implements TribeInfoContract.Presenter {

    private TribeInfoContract.View mView;

    private YWIMKit mIMKit;
    private IYWTribeService mTribeService;
    private YWTribe mTribe;
    private IYWConversationService conversationService;
    private YWConversation conversation;
    private long mTribeId;

    private Handler handler = new Handler();

    TribeInfoPresenter(TribeInfoContract.View v, long mTribeId){
        this.mTribeId = mTribeId;
        mView = v;
        mIMKit = IMKitHelper.getInstance().getIMKit();
        conversationService = mIMKit.getConversationService();
        conversation = conversationService.getTribeConversation(mTribeId);
        mTribeService = mIMKit.getTribeService();
        mTribe = mTribeService.getTribe(mTribeId);
        initTribeChangedListener();
        initContactProfileUpdateListener();
    }

    private Set<String> mContactUserIdSet = new HashSet<>();
    private IContactProfileUpdateListener mContactProfileUpdateListener;
    private void initContactProfileUpdateListener() {
        mContactProfileUpdateListener = new IContactProfileUpdateListener() {
            @Override
            public void onProfileUpdate(String userId, String appKey) {
                if (mContactUserIdSet.contains(userId)) {
                    doPreloadContactProfiles(mList);
                }
            }
            @Override
            public void onProfileUpdate() {
                //just empty
            }
        };

        if(mIMKit!=null&&mIMKit.getContactService()!=null)
            (mIMKit.getContactService()).addProfileUpdateListener(mContactProfileUpdateListener);
        mTribeService.addTribeListener(mTribeChangedListener);
    }

    private Runnable reindexRunnable = new Runnable() {
        @Override
        public void run() {
            if (mView != null)
                mView.updateMembersView(mResult);
        }
    };

    public YWConversation getConversation(){
        return conversation;
    }

    private IYWTribeChangeListener mTribeChangedListener;
    private void initTribeChangedListener() {
        mTribeChangedListener = new IYWTribeChangeListener() {
            @Override
            public void onInvite(YWTribe ywTribe, YWTribeMember ywTribeMember) {

            }

            @Override
            public void onUserJoin(YWTribe tribe, YWTribeMember user) {
                mList.add(user);
                doPreloadContactProfiles(mList);
            }

            @Override
            public void onUserQuit(YWTribe tribe, YWTribeMember user) {
                mList.remove(user);
                doPreloadContactProfiles(mList);
            }

            @Override
            public void onUserRemoved(YWTribe ywTribe, YWTribeMember ywTribeMember) {
                //TODO openTribeList
            }

            @Override
            public void onTribeDestroyed(YWTribe ywTribe) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onQuitTribe();
                        conversationService.deleteConversation(conversation);
                    }
                });

                //TODO openTribeList
            }

            @Override
            public void onTribeInfoUpdated(YWTribe tribe) {
                mTribe = tribe;
                if(mView != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.onProfileUpdate(mTribe);
                        }
                    });
            }

            @Override
            public void onTribeManagerChanged(YWTribe tribe, YWTribeMember user) {
//                String loginUser = mIMKit.getIMCore().getLoginUserId();
//                if (loginUser.equals(user.getUserId())) {
//                    for (YWTribeMember member : mList) {
//                        if (member.getUserId().equals(loginUser)) {
//                            mList.remove(member);
//                            mList.add(user);
//                            mView.updateMembersView(mList);
//                            break;
//                        }
//                    }
//                }
            }

            @Override
            public void onTribeRoleChanged(YWTribe tribe, YWTribeMember user) {  }
        };
    }

    @Override
    public void onDetach() {
        if(mIMKit!=null&&mIMKit.getContactService()!=null)
            (mIMKit.getContactService()).removeProfileUpdateListener(mContactProfileUpdateListener);
        mTribeService.removeTribeListener(mTribeChangedListener);
        mView = null;
    }

    private List<YWTribeMember> mList = new ArrayList<>();
    @Override
    public void getTribeInfoFromServer() {
        mTribeService.getTribeFromServer(new IWxCallback() {
            @Override
            public void onSuccess(final Object... result) {
                mTribe = (YWTribe)result[0];
                if(mView != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.onProfileUpdate(mTribe);
                        }
                    });
            }
            @Override
            public void onError(int code, String info) {  }
            @Override
            public void onProgress(int progress) { }
        }, mTribeId);
    }

    @Override
    public void initTribeInfo() {
        getTribeMembersFromLocal();
        getTribeInfoFromServer();
    }

    private List<IYWContact> mResult;
    private void doPreloadContactProfiles(List<YWTribeMember> data){
        mContactUserIdSet.clear();
        for (YWTribeMember member : data) {
            mContactUserIdSet.add(member.getUserId());
        }
        int length = Math.min(data.size(), IMConstants.PRELOAD_PROFILE_NUM);
        ArrayList<IYWContact> contacts = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            YWTribeMember ywTribeMember = data.get(i);
            contacts.add(ywTribeMember);
        }
        mResult = mIMKit.getContactService().getCrossContactProfileInfos(contacts);
        handler.post(reindexRunnable);
    }

    private void onSuccessGetMembers(final List<YWTribeMember> members){
        if (members == null){
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                mList.addAll(members);
                initLoginUser();
                doPreloadContactProfiles(mList);
            }
        });
    }

    private void getTribeMembersFromLocal() {
        mTribeService.getMembers(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                onSuccessGetMembers((List<YWTribeMember>) result[0]);
                getTribeMembersFromServer();
            }

            @Override
            public void onError(int code, String info) {
                if(mView != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showErrMsg("获取成员失败");
                        }
                    });
            }

            @Override
            public void onProgress(int progress) {

            }
        }, mTribeId);
    }


    private void getTribeMembersFromServer() {
        mTribeService.getMembersFromServer(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                onSuccessGetMembers((List<YWTribeMember>) result[0]);
            }

            @Override
            public void onError(int code, String info) {
                if(mView != null)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showErrMsg("获取成员失败");
                        }
                    });
            }

            @Override
            public void onProgress(int progress) {

            }
        }, mTribeId);
    }

    private YWTribeMember mLoginUser;
    private void initLoginUser(){
        String loginUser = mIMKit.getIMCore().getLoginUserId();
        for (YWTribeMember member : mList) {
            if (member.getUserId().equals(loginUser)) {
                mLoginUser = member;
                break;
            }
        }
    }

    /**
     * 获取登录用户的群昵称
     */
    @Override
    public String getLoginUserTribeNick() {
        if (mLoginUser != null && !TextUtils.isEmpty(mLoginUser.getTribeNick())) {
            return mLoginUser.getTribeNick();
        }
        String tribeNick = null;
        IYWContactService contactService = mIMKit.getContactService();
        IYWContact contact = contactService.getContactProfileInfo(mIMKit.getIMCore().getLoginUserId(), mIMKit.getIMCore().getAppKey());
        if (contact != null){
            if (!TextUtils.isEmpty(contact.getShowName())){
                tribeNick = contact.getShowName();
            } else {
                tribeNick =  contact.getUserId();
            }
        }
        if(TextUtils.isEmpty(tribeNick)) {
            tribeNick = mIMKit.getIMCore().getLoginUserId();
        }
        return tribeNick;
    }

    @Override
    public void setConversation(boolean isTop) {
        if (isTop)
            conversationService.setTopConversation(conversation);
        else
            conversationService.removeTopConversation(conversation);
    }

    private IWxCallback mTribeQuitCallback = new IWxCallback() {
        @Override
        public void onSuccess(Object... objects) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mView.onQuitTribe();
                    conversationService.deleteConversation(conversation);
                }
            });
        }
        @Override
        public void onError(int i, String s) { }
        @Override
        public void onProgress(int i) {  }
    };

    @Override
    public void quitTribe() {
        if (isTribeManager()) {
            mTribeService.disbandTribe(mTribeQuitCallback, mTribeId);
        } else {
            mTribeService.exitFromTribe(mTribeQuitCallback, mTribeId);
        }
    }

    @Override
    public void modifyTribeInfo(final String newData) {
        final IWxCallback callback = new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onTribeInfoModify(newData ,null);
                        mView.showErrMsg("修改群名称完成");
                    }
                });

            }
            @Override
            public void onError(int i, String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showErrMsg("修改群名称失败");
                    }
                });

            }
            @Override
            public void onProgress(int i) { }
        };
        mTribeService.modifyTribeInfo(callback,mTribeId,newData,"");
    }

    @Override
    public void modifyTribeNick(final String newNick) {
        IWxCallback callback = new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onTribeInfoModify(null ,newNick);
                        mView.showErrMsg("修改我的群名片完成");
                    }
                });
            }
            @Override
            public void onError(int i, String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showErrMsg("修改我的群名片失败");
                    }
                });
            }
            @Override
            public void onProgress(int i) {  }
        };
        String loginID = mIMKit.getIMCore().getLoginUserId();
        IYWContact contact = mIMKit.getContactService().getWXIMContact(loginID);
        mTribeService.modifyTribeUserNick(mTribeId,contact,newNick,callback);
    }

    @Override
    public void setMsgNotify(boolean notify) {
        if (!notify)
            mTribeService.unblockTribe(mTribe,null);
        else
            mTribeService.receiveNotAlertTribeMsg(mTribe, null);
    }

    boolean isTribeManager(){
        return getLoginUserRole() == YWTribeRole.TRIBE_HOST.type;
    }

    private int getLoginUserRole() {
        if(mTribe.getTribeRole() == null)
            return YWTribeRole.TRIBE_MEMBER.type;
        return mTribe.getTribeRole().type;
    }

}
