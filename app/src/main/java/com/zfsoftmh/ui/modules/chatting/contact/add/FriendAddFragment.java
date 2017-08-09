package com.zfsoftmh.ui.modules.chatting.contact.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.channel.cloud.contact.YWProfileInfo;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactService;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.NetworkUtils;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.QrCodeData;
import com.zfsoftmh.entity.ZxCode;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.contact.TitleChangeInterface;
import com.zfsoftmh.ui.modules.chatting.contact.profile.ProFileFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.web.WebActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sy
 * on 2017/5/4.
 */
public class FriendAddFragment extends BaseFragment {

    public static FriendAddFragment newInstance(){
        return new FriendAddFragment();
    }

    @Override
    protected void initVariables() {
        setHasOptionsMenu(true);
    }

    private TitleChangeInterface titleChangeInterface;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        titleChangeInterface = (TitleChangeInterface) context;
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_friend_add;
    }

    private EditText editText;
    private View mView;

    @Override
    protected void initViews(View view) {
        titleChangeInterface.onFragmentActive("添加好友");
        mView = view;
        editText = (EditText) view.findViewById(R.id.fad_et);
        ImageView imageView = (ImageView) view.findViewById(R.id.fad_zx);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.fad_my_icon);

        int width = ScreenUtils.getScreenWidth(context) / 2 + 56;

        YWIMCore mIMCore = IMKitHelper.getInstance().getIMKit().getIMCore();
        IYWContact contact = mIMCore.getContactService().
                getContactProfileInfo(mIMCore.getLoginUserId(), IMKitHelper.APP_KEY);

        FriendZxInfo zxInfo = new FriendZxInfo(contact.getUserId(),contact.getShowName(),contact.getAppKey());
        QrCodeData data = new QrCodeData(ZxCode.CODE_FRIEND_ADD);//加上Code类别
        data.setContent(GsonHelper.objectToString(zxInfo));

        imageView.setImageBitmap(CodeUtils.createImage(GsonHelper.objectToString(data), width, width, null));
        ImageLoaderHelper.loadConfigImage(getActivity(), circleImageView,
                contact.getAvatarPath(), 0, true, false, true, false);

        view.findViewById(R.id.fad_button).setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if(TextUtils.isEmpty(editText.getText())){
                    showToastMsgShort("请输入帐号");
                }else{
                    searchContent(editText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    /**
     * 查询帐号
     * @param target 查询目标
     */
    private void searchContent(String target){
        if (!NetworkUtils.isConnected(context)){
            Snackbar.make(mView,getResources().getString(R.string.aliwx_net_null),
                    Snackbar.LENGTH_SHORT).show();
        }else{
            String userId = target.trim();
            ArrayList<String> userIds = new ArrayList<>();
            userIds.add(userId);
            showProgressDialog("正在查询...");
            IYWContactService contactService = IMKitHelper.getInstance().getIMKit().getContactService();
            contactService.fetchUserProfile(userIds, IMKitHelper.APP_KEY, new IWxCallback() {
                @Override
                public void onSuccess(Object... result) {
                    if (result != null) {
                        hideProgressDialog();
                        List<YWProfileInfo> profileList = (List<YWProfileInfo>) result[0];
                        if (profileList == null || profileList.isEmpty()) {
                            Snackbar.make(mView,"无法找到该用户",Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        YWProfileInfo mYWProfileInfo = profileList.get(0);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.common_content,
                                        ProFileFragment.newInstance(
                                                mYWProfileInfo.userId,
                                                mYWProfileInfo.nick))
                                .commit();
                    }else{
                        Snackbar.make(mView,"无法找到该用户",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int i, String s) {
                    hideProgressDialog();
                    Snackbar.make(mView,"搜索失败",Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(int i) {

                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_friend_zx,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id._friend_zx){
//            Intent intent = new Intent(context, ScanQrCodeActivity.class);
//            startActivity(intent);
            Intent intent = new Intent(context, WebActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
