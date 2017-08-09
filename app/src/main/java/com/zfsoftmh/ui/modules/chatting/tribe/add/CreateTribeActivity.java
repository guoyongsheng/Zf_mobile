package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeType;
import com.alibaba.mobileim.tribe.IYWTribeService;
import com.alibaba.mobileim.tribe.YWTribeCreationParam;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/16.
 * <p>创建群组</p>
 */
public class CreateTribeActivity extends BaseActivity implements OnMemberSelectCallback{

    private FragmentManager fragmentManager;
    private YWIMKit mIMKit;
    private boolean isFromTribePage;

    @Override
    protected void initVariables() {
        fragmentManager = getSupportFragmentManager();
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        isFromTribePage = bundle.getBoolean("page_tribe",false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("发起群聊");
        setDisplayHomeAsUpEnabled(true);
        MemberSelectFragment fragment = (MemberSelectFragment) fragmentManager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = MemberSelectFragment.newInstance(null);
        }
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment,R.id.common_content);
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void initListener() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    @Override
    public void OnMemberSelect(ArrayList<ChatContact> selectData) {
        createGroup(selectData);
    }

    private List<IYWContact> selectData;
    private IYWTribeService iywTribeService;
    private void createGroup(List<ChatContact> selectMember){
        List<String> users = new ArrayList<>();
        users.add(mIMKit.getIMCore().getLoginUserId());
        for (ChatContact user : selectMember) {
            users.add(user.getUserId());
        }
        selectData = new ArrayList<>();
        for (ChatContact user : selectMember) {
            selectData.add(user);
        }
        YWTribeCreationParam param = new YWTribeCreationParam();
        param.setTribeType(YWTribeType.CHATTING_TRIBE);
        String name =mIMKit.getContactService().getContactProfileInfo(
                mIMKit.getIMCore().getLoginUserId(), IMKitHelper.APP_KEY).getShowName();
        param.setTribeName(name + "的群聊");
        param.setNotice("");
        param.setUsers(users);
        iywTribeService = mIMKit.getTribeService();
        iywTribeService.createTribe(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                onCreateTribeSuccess(result);
            }
            @Override
            public void onError(int i, String s) { }
            @Override
            public void onProgress(int i) { }
        },param);
    }

    Handler handler = new Handler();
    private void onCreateTribeSuccess(Object[] result) {
        if (result != null && result.length > 0) {
            final YWTribe tribe = (YWTribe) result[0];
            setResult(TribeKeys.ON_TRIBES_MODIFY);
            if (isFromTribePage)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iywTribeService.inviteMembers(tribe.getTribeId(), selectData, new IWxCallback() {
                            @Override
                            public void onSuccess(Object... objects) { }
                            @Override
                            public void onError(int i, String s) { }
                            @Override
                            public void onProgress(int i) { }
                        });
                    }
                });
            else
                IMKitHelper.getInstance().startTribeChatting(this,tribe.getTribeId());
        }
        finish();
    }
}
