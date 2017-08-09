package com.zfsoftmh.ui.modules.chatting.location;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.amap.api.services.core.PoiItem;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sy
 * on 2017/5/31.
 * <p>发送位置</p>
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LocationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, PoiChoiceCallBack {

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private final int REQUEST_CODE = 0;
    private YWIMKit mIMKit;
     FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        String conversationID = getIntent().getStringExtra("conversation_id");
        mConversation = mIMKit.getConversationService().getConversationByConversationId(conversationID);
        if (mConversation == null) {
            this.finish();
            IMNotificationUtils.getInstance().showToast(this, "出错了,请稍后再试");
        }
//        conversationType = mConversation.getConversationType();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("位置");
        setDisplayHomeAsUpEnabled(true);
        EasyPermissions.requestPermissions(this, "需要定位权限", REQUEST_CODE, needPermissions);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    @Override
    protected void setUpInject() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE) {
            LocationFragment fragment;
            fragment = (LocationFragment) manager.findFragmentById(R.id.common_content);
            if (fragment == null) {
                fragment = LocationFragment.newInstance();
                ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private AppSettingsDialog appSettingsDialog;

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (appSettingsDialog == null) {
                appSettingsDialog = new AppSettingsDialog
                        .Builder(this)
                        .setTitle(R.string.request_permissions)
                        .setRationale(R.string.permissions_rationale)
                        .setPositiveButton(R.string.Ok)
                        .setNegativeButton(R.string.cancel)
                        .build();
            }
            appSettingsDialog.show();
        }
    }

//    private YWConversationType conversationType;
    private YWConversation mConversation;

    @Override
    public void onPoiChoice(PoiItem poiItem) {
        YWMessage message = YWMessageChannel.createGeoMessage(poiItem.getLatLonPoint().getLatitude(),
                poiItem.getLatLonPoint().getLongitude(), poiItem.getTitle()+"n"+poiItem.getSnippet());
        if (mConversation != null) {
            mConversation.getMessageSender().sendMessage(message, 120, null);
        }
        finish();


    }


}
