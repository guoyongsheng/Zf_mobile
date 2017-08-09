package com.zfsoftmh.ui.modules.chatting.location;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ljq
 * on 2017/6/7.
 */

public class OpenGeoMessageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

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

    FragmentManager manager;
    private final int REQUEST_CODE = 0;
    private double latitude;
    private double longitude;
    private String address;
    @Override
    protected void initVariables() {
      manager=getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        latitude=bundle.getDouble("latitude");
        longitude=bundle.getDouble("longitude");
        address=bundle.getString("address");
    }



    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
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
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE) {
            OpenGeoFragment fragment;
            fragment = (OpenGeoFragment) manager.findFragmentById(R.id.common_content);
            if (fragment == null) {
                fragment = OpenGeoFragment.newInstance();
                Bundle bundle=new Bundle();
                bundle.putDouble("latitude",latitude);
                bundle.putDouble("longitude",longitude);
                bundle.putString("address",address);
                fragment.setArguments(bundle);
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



}
