package com.zfsoftmh.ui.modules.web;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DateUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: web模块界面
 */

public class WebActivity extends BaseActivity implements WebFragment.OnShareClickListener,
        EasyPermissions.PermissionCallbacks {

    private static final String TAG = "WebActivity";
    private static final int REQUEST_CODE_PERMISSIONS = 1; //分享授权的请求码
    private String loadUrl; // url
    private String title;  // 标题
    private String image_url; //图片的url
    private FragmentManager manager;
    private WebFragment fragment;
    private ProgressDialog dialog;
    private SHARE_MEDIA share_media;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.sharing));
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        String url = bundle.getString(Config.WEB.URL, "");
        title = bundle.getString(Config.WEB.TITLE, "");
        image_url = bundle.getString(Config.WEB.IMAGE_URL, "");
        String proCode = bundle.getString(Config.WEB.PRO_CODE);
        loadUrl = getLoadUrl(url, proCode);
    }

    private String getLoadUrl(String url, String proCode) {
        String url_load = null;
        if (TextUtils.isEmpty(proCode)) {
            url_load = getUrl(url);//这里还没拼接TGC : "....&tgc=$value"
        } else {
            try {
                url_load = getUrl(url, proCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoggerHelper.e("load_url", url_load);
        return url_load;
    }

    public String getUrl(String mUrl, String proCode) throws Exception {
        String app_token = DbHelper.getAppToken(this);
        if (TextUtils.isEmpty(app_token)) {
            return null;
        }
        String _proCode = CodeUtil.getEncodedValueWithToken(proCode, app_token);
        return Config.URL.MOBILE_OTHER_FLAG
                + "access_token=" + java.net.URLEncoder.encode(app_token, "UTF-8")
                + "&procode=" + java.net.URLEncoder.encode(_proCode, "UTF-8")
                + "&redirect_uri=" + java.net.URLEncoder.encode(mUrl, "UTF-8");
    }

    private String getUrl(String url) {
        String theTime = DateUtils.getFullTimeStr(Calendar.getInstance());
        String theUrl = url + "&" + "time=" + theTime;
        Map<String, String> map = getMapParam(url);// 新增
        String urlProCode;
        String urlChose;
        String urlUid;
        if (theUrl.contains("?")) {
            urlProCode = map.get("procode");
            urlChose = map.get("choice");
            urlUid = map.get("uid");
            if (urlProCode != null && urlProCode.equals("002")) {
                String jwUrl;
                Date date = new Date();
                long time = date.getTime();
                String time1 = String.valueOf(time);
                String jwTime = time1.substring(0, 10);
                String publicKey = MD5Util.getMD5ofStr(urlProCode + urlChose
                        + urlUid + "DAFF8EA19E6BAC86E040007F01004EA" + jwTime);
                jwUrl = url + "&" + "key=" + publicKey + "&" + "time=" + jwTime;
                return jwUrl;
            } else if (urlProCode != null && urlProCode.equals("006")) {
                String publicKey = MD5Util_OA.Encrypt(urlProCode + urlChose
                        + urlUid + theTime + "DAFF8EA19E6BAC86E040007F01004EA");
                return url + "&" + "key=" + publicKey + "&" + "time=" + theTime;
            } else {
                return theUrl;
            }
        } else {
            return url;
        }
    }

    public Map<String, String> getMapParam(String url) {
        int index = url.indexOf("?");
        int index2;
        if (TextUtils.isEmpty(url) || index == -1) {
            return null;
        }
        HashMap<String, String> params = new HashMap<>();
        url = url.substring(index + 1);
        String[] paramArray = url.split("&");
        for (String param : paramArray) {
            index2 = param.indexOf("=");
            if (index2 <= 0) {
                continue;
            }
            params.put(param.substring(0, index2), param.substring(index2 + 1));
        }
        return params;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(title);
        setDisplayHomeAsUpEnabled(true);

        fragment = (WebFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = WebFragment.newInstance(loadUrl, title);
//            fragment.setOnTitleChangeListener(this);
            fragment.setOnShareClickListener(this);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
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
        dealWithBackPressed();
    }

    @Override
    public void onBackPressed() {
        dealWithBackPressed();
    }


    //处理返回键
    private void dealWithBackPressed() {
        if (fragment != null && fragment.canGoBack()) {
            fragment.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onShareClick() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.ALIPAY)
                .setShareboardclickCallback(shareBoardlistener)
                .open(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //回调接口
    private UMShareListener listener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onStart share_media = " + share_media);
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onResult share_media = " + share_media);
            SocializeUtils.safeCloseDialog(dialog);
            showToastMsgShort(getResources().getString(R.string.share_success));
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            LoggerHelper.e(TAG, " onError share_media = " + share_media + " throwable = " + throwable.getMessage());
            SocializeUtils.safeCloseDialog(dialog);
            showToastMsgShort(getResources().getString(R.string.share_failure));
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onCancel share_media = " + share_media);
            SocializeUtils.safeCloseDialog(dialog);
            showToastMsgShort(getResources().getString(R.string.share_cancel));
        }
    };

    //分享面板item的点击事件
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

            WebActivity.this.share_media = share_media;
            requestPermissions();
        }
    };

    //请求权限
    private void requestPermissions() {

        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.GET_ACCOUNTS};

        String rationale = getResources().getString(R.string.share_and_auth_need_permissions);
        EasyPermissions.requestPermissions(this, rationale, REQUEST_CODE_PERMISSIONS, mPermissionList);
    }

    //创建对话框
    private void createAppSettingDialog() {
        showAppSettingDialog();
    }

    //分享
    private void share(SHARE_MEDIA share_media) {
        UMImage umImage;
        if (image_url == null || image_url.length() == 0) {
            umImage = new UMImage(this, R.mipmap.logo);
        } else {
            umImage = new UMImage(this, image_url);
        }
        umImage.compressStyle = UMImage.CompressStyle.SCALE;
        UMWeb umWeb = new UMWeb(loadUrl);
        umWeb.setTitle(title);
        umWeb.setDescription("这是一个分享的描述");
        umWeb.setThumb(umImage);
        new ShareAction(WebActivity.this)
                .withMedia(umWeb)
                .setPlatform(share_media)
                .setCallback(listener)
                .share();
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 分享授权
         */
        case REQUEST_CODE_PERMISSIONS:
            share(share_media);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
