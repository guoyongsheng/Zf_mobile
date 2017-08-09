package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWCustomMessageBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.google.gson.Gson;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.NetworkUtils;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.modules.chatting.custom.Chatting_Operation;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.helper.ImageMsgHelper;
import com.zfsoftmh.ui.modules.chatting.helper.view.forward.CustomMsg;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sy
 * on 2017/7/5.
 * <p>文件浏览</p>
 */

public class FileExplorerActivity extends BaseActivity implements
        EasyPermissions.PermissionCallbacks,FileSender{

    private YWIMKit mIMKit;
    private YWConversation mConversation;
    private YWConversationType conversationType;
    private ImageMsgHelper helper;
    @Override
    protected void initVariables() {
        mIMKit = IMKitHelper.getInstance().getIMKit();
        helper = new ImageMsgHelper();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        String conversationID = getIntent().getStringExtra("conversation_id");
        mConversation = mIMKit.getConversationService().getConversationByConversationId(conversationID);
        if (mConversation == null){
            this.finish();
            IMNotificationUtils.getInstance().showToast(this, "出错了,请稍后再试");
        }
        conversationType = mConversation.getConversationType();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_agency_matters;
    }

    private final int REQUEST_CODE_FILE = 2>>2;
    private TabLayout tabLayout;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("选择文件");
        setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.activity_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("文档"));
        tabLayout.addTab(tabLayout.newTab().setText("音频"));
        tabLayout.addTab(tabLayout.newTab().setText("视频"));
        tabLayout.addTab(tabLayout.newTab().setText("图片"));

        EasyPermissions.requestPermissions(this,"浏览文件需要相应的权限",
                REQUEST_CODE_FILE, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void initListener() { }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_FILE){
            loadView();
        }
    }

    private void loadView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_agency_view_pager);
        fragments.add(NormalFileFragment.newInstance());
        fragments.add(MusicFileFragment.newInstance());
        fragments.add(VideoFileFragment.newInstance());
        fragments.add(PicFileFragment.newInstance());
        ArrayList<String> titles = new ArrayList<>();
        titles.add("文档");
        titles.add("音频");
        titles.add("视频");
        titles.add("图片");
        viewPager.setAdapter(new FileTypeViewPagerAdapter(getSupportFragmentManager(),fragments,titles));
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            showAppSettingDialog();
    }


    @Override
    public void sendFile(int type, FileInfo info) {
        if (type == 4){//image message
            ImageMsgHelper helper = new ImageMsgHelper();
            YWMessage msg = helper.getMessage(this,info.getFilePath());
            if (msg!=null && mConversation!= null) {
                mConversation.getMessageSender().sendMessage(msg, 120, null);
                finish();
            }else
                Toast.makeText(this,"图片发送失败",Toast.LENGTH_SHORT).show();
        }else{
            showAlertDialog(type,info);
        }
    }

    private void showAlertDialog(final int type, final FileInfo info) {
        if (NetworkUtils.isConnected(BaseApplication.getContext())){
            if (NetworkUtils.isNetWorkWifiState(BaseApplication.getContext())){
                postFile(type,info);
            }else{
                new YWAlertDialog.Builder(this)
                        .setMessage(getString(R.string.on_not_wifi_alert))
                        .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                postFile(type, info);
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(true).create().show();
            }
        }else{
            Toast.makeText(this,"网络未连接",Toast.LENGTH_SHORT).show();
        }
    }

    private AlertDialog dialog;
    private void postFile(final int type, final FileInfo info) {
        if (dialog == null){
            dialog = new ProgressDialog.Builder(this)
                    .setMessage("文件上传中...")
                    .setCancelable(false)
                    .create();
        }
        dialog.show();
        helper.uploadFile(info, new CallBackListener<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                dialog.dismiss();
                Gson gson = new Gson();
                String url = "http://www.baidu.com";
                YWCustomMessageBody messageBody = new YWCustomMessageBody();
                CustomMsg msg = new CustomMsg();
                msg.customizeMessageType = Chatting_Operation.CustomMessageType.FILE;
                msg.arg0 = info.getFileName();
                msg.arg1 = url;
                msg.arg2 = info.getFilePath();
                msg.arg3 = String.valueOf(type);
                if (type == 1)
                    msg.arg4 = info.getSuffixType();
                msg.arg5 = info.getFileSize();
                String cusData = gson.toJson(msg);
                messageBody.setContent(cusData);
                messageBody.setSummary("[文件]");
                YWMessage message = null;
                if(conversationType == YWConversationType.P2P){
                    message = YWMessageChannel.createCustomMessage(messageBody);
                }else if(conversationType == YWConversationType.Tribe){
                    message = YWMessageChannel.createTribeCustomMessage(messageBody);
                }
                if (message != null && mConversation != null)
                    mConversation.getMessageSender().sendMessage(message, 120, null);
                finish();
            }

            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                Toast.makeText(FileExplorerActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
