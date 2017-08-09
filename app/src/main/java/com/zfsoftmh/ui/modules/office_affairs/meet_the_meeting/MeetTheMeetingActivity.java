package com.zfsoftmh.ui.modules.office_affairs.meet_the_meeting;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 会议签到界面
 */

public class MeetTheMeetingActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS = 1;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.meet_the_meeting);
        setDisplayHomeAsUpEnabled(true);

        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_scan_qr_code),
                REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS, Manifest.permission.CAMERA);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 扫描二维码
         */
        case REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS:

            break;

        default:
            break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showAppSettingDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
