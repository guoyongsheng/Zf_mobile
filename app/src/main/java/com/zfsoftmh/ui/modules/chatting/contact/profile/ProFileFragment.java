package com.zfsoftmh.ui.modules.chatting.contact.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWDBContact;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.contact.TitleChangeInterface;
import com.zfsoftmh.ui.modules.chatting.helper.FriendAddAcceptListener;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;

/**
 * Created by sy
 * on 2017/5/4.
 */
public class ProFileFragment extends BaseFragment implements FriendAddAcceptListener {

    private TitleChangeInterface titleChangeInterface;
    private YWContactHeadLoadHelper mHelper;
    private static final String KEY_TARGET_ID = "targetID";
    private static final String KEY_NICK = "nick";

    public static ProFileFragment newInstance(String targetID,String nick){
        ProFileFragment fragment = new ProFileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TARGET_ID,targetID);
        bundle.putString(KEY_NICK,nick);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        titleChangeInterface = (TitleChangeInterface) context;
    }

    @Override
    protected void initVariables() {
        IMKitHelper.getInstance().getIMKit().getContactService()
                .addContactCacheUpdateListener(new IYWContactCacheUpdateListener() {
                    @Override
                    public void onFriendCacheUpdate(String s, String s1) {
                        initData();
                    }
                });
        mHelper = new YWContactHeadLoadHelper(this.getActivity(), null,
                IMKitHelper.getInstance().getIMKit().getUserContext());
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        mTargetID = bundle.getString(KEY_TARGET_ID);
        mNick = bundle.getString(KEY_NICK);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_chat_contact_profile;
    }

    private ImageView mHeadView;
    private Button mBtnAdd, mBtnSend;
    private String mTargetID,mNick;
    private TextView profileUserID,profileUserNick;
    private View mView;

    @Override
    protected void initViews(View view) {
        titleChangeInterface.onFragmentActive("资料");
        mView = view;
        ImageView mHeadBgView = (ImageView) view.findViewById(R.id.profile_block_bg);
        mHeadBgView.setImageResource(R.drawable.aliwx_head_bg_0);
        mHeadView = (ImageView) view.findViewById(R.id.profile_people_head);
        profileUserID = (TextView) view.findViewById(R.id.profile_user_text);
        profileUserNick = (TextView) view.findViewById(R.id.remark_name_text);
        mBtnAdd = (Button) view.findViewById(R.id.profile_btn_add);
        mBtnSend = (Button) view.findViewById(R.id.profile_btn_send);
        initData();
        IMKitHelper.getInstance().getContactOperateNotifyListener().setFriendAddAcceptListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        IMKitHelper.getInstance().getContactOperateNotifyListener().removeFriendAddAcceptListener();
    }

    private void initData() {
        profileUserID.setText(mTargetID);
        profileUserNick.setText(mNick);
        mHelper.setHeadView(mHeadView, mTargetID, IMKitHelper.APP_KEY, true);
        checkContact(mTargetID);
    }

    private void checkContact(String targetID){
        if (!TextUtils.equals(targetID,
                IMKitHelper.getInstance().getIMKit().getIMCore().getLoginUserId())){
            //不是自己
            List<IYWDBContact> contactsFromCache = IMKitHelper.getInstance().getIMKit()
                    .getContactService().getContactsFromCache();
            for(IYWDBContact contact: contactsFromCache){
                if(contact.getUserId().equals(targetID)){
                    mBtnSend.setVisibility(View.VISIBLE);
                    return;
                }
            }
            mBtnSend.setVisibility(View.VISIBLE);
            mBtnAdd.setVisibility(View.VISIBLE);
        }else{
            Snackbar.make(mView,"这是您自己",Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    protected void initListener() {
        mBtnAdd.setOnClickListener(clickListener);
        mBtnSend.setOnClickListener(clickListener);
    }

    private OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            switch (v.getId()){
                case R.id.profile_btn_add:
                    sendAddContactRequest();
                    break;
                case R.id.profile_btn_send:
                    IMKitHelper.getInstance().startChatting(getActivity(),mTargetID);
                    getActivity().finish();
                    break;
            }
        }
    };

    private void sendAddContactRequest() {
        YWIMKit imKit = IMKitHelper.getInstance().getIMKit();
        String mID = imKit.getIMCore().getLoginUserId();
        IYWContact contact = imKit.getContactService().
                getContactProfileInfo(mID, IMKitHelper.APP_KEY);
        String name = contact.getShowName();
        String mMsg;
        if (!TextUtils.isEmpty(name))
            mMsg = "我是" + name;
        else
            mMsg = "加个好友呗";
        IMKitHelper.getInstance().getIMKit().getContactService()
                .addContact(mTargetID, IMKitHelper.APP_KEY, mNick, mMsg, new IWxCallback() {
                    @Override
                    public void onSuccess(Object... objects) {
                        Snackbar.make(mView, "好友请求已发送", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(int i, String s) {
                        Snackbar.make(mView, "好友请求发送失败", Snackbar.LENGTH_LONG).show();
                        LoggerHelper.e("发送好友请求失败", s);
                    }

                    @Override
                    public void onProgress(int i) {

                    }
                });
    }

    @Override
    public void onAccept(IYWContact contact) {
        String showName = contact.getShowName();
        if(TextUtils.isEmpty(showName)) showName = contact.getUserId();
        Snackbar.make(mView,showName + "接受了您的好友请求",Snackbar.LENGTH_SHORT).show();
        if (mBtnAdd.getVisibility() == View.VISIBLE)
            mBtnAdd.setVisibility(View.GONE);
    }

    @Override
    public void onRefuse() {
        Snackbar.make(mView,"对方拒绝了您的好友请求",Snackbar.LENGTH_SHORT).show();
    }
}
