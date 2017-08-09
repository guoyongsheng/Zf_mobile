package com.zfsoftmh.ui.modules.personal_affairs.qr_code;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.mobileim.channel.event.IWxCallback;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.QrCodeData;
import com.zfsoftmh.entity.ZxCode;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.add.FriendZxInfo;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.profile.TribeMemberProfileActivity;
import com.zfsoftmh.ui.modules.chatting.tribe.profile.TribeZxInfo;
import com.zfsoftmh.ui.modules.personal_affairs.portal_certification.PortalCertificationActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 扫描二维码界面
 */

public class ScanQrCodeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 1;
    private static final java.lang.String TAG = "ScanQrCodeActivity";
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 2;

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

    View parent;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.scan_qr_code);
        setDisplayHomeAsUpEnabled(true);
        parent = findViewById(R.id.common_content);
        FragmentManager manager = getSupportFragmentManager();
        CaptureFragment captureFragment = (CaptureFragment) manager.findFragmentById(R.id.common_content);
        if (captureFragment == null) {
            captureFragment = new CaptureFragment();
            CodeUtils.setFragmentArgs(captureFragment, R.layout.scan_qr_code_style);
            captureFragment.setAnalyzeCallback(analyzeCallback);
            ActivityUtils.addFragmentToActivity(manager, captureFragment, R.id.common_content);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_qr_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 从相册中选取
         */
        case R.id.menu_scan_qr_code_album:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                EasyPermissions.requestPermissions(this,
                        getResources().getString(R.string.request_permission_by_select_from_album),
                        REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS, Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                openActivity();
            }
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            decodeQrCode(result);
        }

        @Override
        public void onAnalyzeFailed() {
            showErrorMsg();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 从相册中选取
         */
        case REQUEST_CODE_IMAGE_FROM_ALBUM:
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            decodeQrCode(result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            showErrorMsg();
                        }
                    });
                } catch (Exception e) {
                    LoggerHelper.e(TAG, " onActivityResult 二维码解析失败 " + e.getMessage());
                }
            }
            break;

        default:
            break;
        }
    }

    //显示二维码解析失败的信息
    private void showErrorMsg() {
        showToastMsgShort(getResources().getString(R.string.scan_qr_code_error));
        finish();
    }

    //解析二维码
    private void decodeQrCode(String result) {
        Pattern p = Pattern.compile(getString(R.string.pattern_regex));
        Matcher m = p.matcher(result);
        if (m.matches()) {
        QrCodeData data = GsonHelper.stringToObject(result, QrCodeData.class);
        switch (data.getCode()) {
        case ZxCode.CODE_FRIEND_ADD:
            FriendZxInfo zxInfo = GsonHelper.stringToObject(data.getContent(), FriendZxInfo.class);
            if (zxInfo.getAppKey().equals(IMKitHelper.APP_KEY)) {
                openContactProfile(zxInfo);
            } else {
                Snackbar.make(parent, R.string.unable_fad_dif_key, Snackbar.LENGTH_SHORT).show();
            }
            break;
        case ZxCode.CODE_TRIBE_ADD:
            TribeZxInfo info = GsonHelper.stringToObject(data.getContent(), TribeZxInfo.class);
            if (info.appKey.equals(IMKitHelper.APP_KEY)) {
                requestToJoinTribe(info.tribeID);
            } else {
                Snackbar.make(parent, R.string.unable_fad_dif_key, Snackbar.LENGTH_SHORT).show();
            }
            break;
        case ZxCode.CODE_URL:
            openWeb(data.getContent());
            break;
        case ZxCode.CODE_TEXT:

            break;
        case ZxCode.CODE_PHONE_CONTACT:

            break;


        case ZxCode.CODE_PORTAL_CERTIFICATION:
            openPortalCertification(data.getContent(), data.getCallback());
            break;
        default:

            break;
        }
        } else {
            //TODO 其他内容
        }
    }

    /**
     * 主动加入群
     */
    private void requestToJoinTribe(final long tribeID) {
        IMKitHelper.getInstance().getIMKit().getTribeService().joinTribe(new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                IMKitHelper.getInstance().startTribeChatting(ScanQrCodeActivity.this, tribeID);
                finish();
            }

            @Override
            public void onError(int i, String s) {
                if (i == 6) {//本来就在群里面
                    IMKitHelper.getInstance().startTribeChatting(ScanQrCodeActivity.this, tribeID);
                    finish();
                } else {
                    Snackbar.make(parent, "加入群失败", Snackbar.LENGTH_SHORT).show();
                }
                LoggerHelper.e("joinTribe", s);
            }

            @Override
            public void onProgress(int i) {
            }
        }, tribeID);
    }


    /**
     * 打开网址
     */
    private void openWeb(String url) {
        Intent intent = new Intent(ScanQrCodeActivity.this, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, url);
        bundle.putString(Config.WEB.TITLE, getResources().getString(R.string.scan_result));
        intent.putExtras(bundle);
        openActivity(intent);
        finish();
    }

    /**
     * 打开个人profile
     */
    private void openContactProfile(FriendZxInfo zxInfo) {
        Intent intent = new Intent(ScanQrCodeActivity.this, TribeMemberProfileActivity.class);
        intent.putExtra("targetID", zxInfo.getUserId());
        intent.putExtra("targetNick", zxInfo.getShowName());
        startActivity(intent);
        finish();
    }

    //打开门户认证界面
    private void openPortalCertification(String id, String url) {

        Intent intent = new Intent(ScanQrCodeActivity.this, PortalCertificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 从相册中选取
         */
        case REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS:
            openActivity();
            break;

        default:
            break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            createAppSettingDialog();
        }
    }

    //创建对话框
    private void createAppSettingDialog() {
        showAppSettingDialog();
    }

    //从相册中选取
    private void openActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
