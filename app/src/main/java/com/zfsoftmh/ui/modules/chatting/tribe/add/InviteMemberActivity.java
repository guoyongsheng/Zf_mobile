package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/23.
 * <p>邀请群成员</p>
 */
public class InviteMemberActivity extends BaseActivity implements OnMemberSelectCallback {

    private FragmentManager fragmentManager;
    private YWIMKit mIMKit;
    @Override
    protected void initVariables() {
        fragmentManager = getSupportFragmentManager();
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    private long mTribeID;
    private ArrayList<String> memberIDs;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        mTribeID = getIntent().getLongExtra("targetTribe",0);
        memberIDs = getIntent().getStringArrayListExtra("data_id");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("添加成员");
        setDisplayHomeAsUpEnabled(true);
        MemberSelectFragment fragment = (MemberSelectFragment) fragmentManager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = MemberSelectFragment.newInstance(memberIDs);
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment,R.id.common_content);
        }
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    private List<IYWContact> mSelectData;

    @Override
    public void OnMemberSelect(ArrayList<ChatContact> selectMember) {
        mSelectData = new ArrayList<>();
        for (ChatContact user : selectMember) {
            mSelectData.add(user);
        }
        if (mSelectData.size() > 0)
            inviteMembers();
    }
    Handler handler = new Handler();

    private void inviteMembers(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                mIMKit.getTribeService().inviteMembers(mTribeID, mSelectData, new IWxCallback() {
                    @Override
                    public void onSuccess(Object... objects) { }
                    @Override
                    public void onError(int i, String s) { }
                    @Override
                    public void onProgress(int i) { }
                });
            }
        });
        finish();
    }

    @Override
    protected void setUpInject() { }
    @Override
    protected void initListener() { }
}
