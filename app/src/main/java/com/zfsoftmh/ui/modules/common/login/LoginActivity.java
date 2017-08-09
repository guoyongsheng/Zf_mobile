package com.zfsoftmh.ui.modules.common.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 登录界面
 */
public class LoginActivity extends BaseActivity implements LoginFragment.OnViewClickListener,
        EasyPermissions.PermissionCallbacks {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_CODE_QQ = 1;
    private static final int REQUEST_CODE_WEIXIN = 2;
    private static final int REQUEST_CODE_SINA = 3;
    private LoginFragment fragment;
    private FragmentManager manager;
    private int from; // 0: 用户没有登录  1: token_error 2:退出登录
    private ProgressDialog dialog;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.auth_loading));

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        from = bundle.getInt("from");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.login);
        setDisplayHomeAsUpEnabled(true);

        fragment = (LoginFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = LoginFragment.newInstance(from);
            fragment.setOnViewClickListener(this);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerLoginComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(fragment))
                .build()
                .inject(this);
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
    public void onQQClick() {
        if (!checkIsInstall(0)) {
            showToastMsgShort(getResources().getString(R.string.qq_not_install));
            return;
        }
        requestPermissions(REQUEST_CODE_QQ);
    }

    @Override
    public void onWeiXinClick() {
        if (!checkIsInstall(1)) {
            showToastMsgShort(getResources().getString(R.string.weixin_not_install));
            return;
        }
        requestPermissions(REQUEST_CODE_WEIXIN);
    }

    @Override
    public void onSinaClick() {
        if (!checkIsInstall(2)) {
            showToastMsgShort(getResources().getString(R.string.sina_not_install));
            return;
        }
        requestPermissions(REQUEST_CODE_SINA);
    }

    //回调接口
    private UMAuthListener listener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onStart share_media = " + share_media);
            showProgressDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            LoggerHelper.e(TAG, " onComplete share_media = " + share_media + " i = " + i + " map = " + map);
            hideProgressDialog();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            LoggerHelper.e(TAG, " onError share_media = " + share_media + " i = " + i + " " +
                    "throwable = " + throwable.getMessage());
            hideProgressDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            LoggerHelper.e(TAG, " onCancel share_media = " + share_media + " i = " + i);
            hideProgressDialog();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    //显示对话框
    private void showProgressDialog() {
        SocializeUtils.safeShowDialog(dialog);
    }

    //隐藏对话框
    private void hideProgressDialog() {
        SocializeUtils.safeCloseDialog(dialog);
    }

    //检测客户端是否安装
    private boolean checkIsInstall(int type) {
        switch (type) {
        /*
         * QQ
         */
        case 0:
            return UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ);

        /*
         * WEIXIN
         */
        case 1:
            return UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN);

        /*
         * SINA
         */
        case 2:
            return UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.SINA);

        default:
            break;
        }

        return false;
    }

    //请求权限
    private void requestPermissions(int requestCode) {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.GET_ACCOUNTS};

        String rationale = getResources().getString(R.string.share_and_auth_need_permissions);
        EasyPermissions.requestPermissions(this, rationale, requestCode, mPermissionList);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         *  QQ授权
         */
        case REQUEST_CODE_QQ:
            UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.QQ, listener);
            break;

        /*
         *  微信授权
         */
        case REQUEST_CODE_WEIXIN:
            UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, listener);
            break;

        /*
         * 新浪授权
         */
        case REQUEST_CODE_SINA:
            UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.SINA, listener);
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
