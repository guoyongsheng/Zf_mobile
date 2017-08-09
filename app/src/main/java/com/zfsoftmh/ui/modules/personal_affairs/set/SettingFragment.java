package com.zfsoftmh.ui.modules.personal_affairs.set;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.PhoneUtils;
import com.zfsoftmh.entity.IsLogin;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.ChattingSetActivity;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.common.login.LoginActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.about.AboutActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.common_setting.CommonSettingActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.feedback.FeedBackActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.shareapp.ShareAppActivity;
import com.zfsoftmh.ui.service.VersionUpdateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: ui展示
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View,
        View.OnClickListener, EasyPermissions.PermissionCallbacks, SettingDialogFragment.OnUpdateClickListener {

    private static final int VERSION_CHECK_REQUEST_CODE = 1; //版本跟新的请求码
    private static final String SET_DIALOG_FRAGMENT = "setting_dialog_fragment";
    private RelativeLayout rl_check_version; //版本检测
    private ImageView iv_check_version; //版本检测的小图标
    private RelativeLayout rl_account;  //账户与安全
    private RelativeLayout rl_common_set; //通用设置
    private RelativeLayout rl_clear_cache; //清除缓存
    private TextView tv_cache_size; //緩存大小
    private RelativeLayout rl_suggestion; //意见反馈
    private RelativeLayout rl_about_us; //关于我们
    private RelativeLayout rl_exit_login; //退出登录
    private RelativeLayout rl_chat_set;//聊天设置
    private RelativeLayout rl_shareapp;//分享app

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initViews(View view) {
        rl_check_version = (RelativeLayout) view.findViewById(R.id.setting_check_version);
        iv_check_version = (ImageView) view.findViewById(R.id.setting_check_version_icon);
        rl_account = (RelativeLayout) view.findViewById(R.id.setting_account_and_save);
        rl_common_set = (RelativeLayout) view.findViewById(R.id.setting_common_set);
        rl_clear_cache = (RelativeLayout) view.findViewById(R.id.setting_clear_cache);
        tv_cache_size = (TextView) view.findViewById(R.id.setting_cache_size);
        rl_suggestion = (RelativeLayout) view.findViewById(R.id.setting_suggestion);
        rl_about_us = (RelativeLayout) view.findViewById(R.id.setting_about_us);
        rl_exit_login = (RelativeLayout) view.findViewById(R.id.setting_exit_login);
        rl_chat_set = (RelativeLayout) view.findViewById(R.id.setting_chat_set);
        rl_shareapp= (RelativeLayout) view.findViewById(R.id.setting_shareapp);
        tv_cache_size.setText(getCacheSize());
    }

    @Override
    protected void initListener() {
        rl_check_version.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_common_set.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        rl_suggestion.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        rl_exit_login.setOnClickListener(this);
        rl_chat_set.setOnClickListener(this);
        rl_shareapp.setOnClickListener(this);
    }

    /**
     * 实例化对象
     *
     * @return SettingFragment对象
     */
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         *  版本检测
         */
        case R.id.setting_check_version:
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_rationale),
                    VERSION_CHECK_REQUEST_CODE, Manifest.permission.READ_PHONE_STATE);
            break;

        /*
         *  账户与安全
         */
        case R.id.setting_account_and_save:

            break;

        /*
         * 通用设置
         */
        case R.id.setting_common_set:
            startActivity(CommonSettingActivity.class);
            break;

        /*
         * 清除缓存
         */
        case R.id.setting_clear_cache:
            clearCache();
            break;

        /*
         * 意见反馈
         */
        case R.id.setting_suggestion:
            startActivity(FeedBackActivity.class);
            break;

        /*
         * 关于我们
         */
        case R.id.setting_about_us:
            startActivity(AboutActivity.class);
            break;

        /*
         * 聊天设置
         */
        case R.id.setting_chat_set:
            startActivity(ChattingSetActivity.class);
             break;

        /*
         * 退出登录
         */
        case R.id.setting_exit_login:
            exitLogin();
            break;

            /**
             * 分享app
             */
            case R.id.setting_shareapp:
                shareApp();
                break;
        default:
            break;
        }
    }

    private void shareApp() {
        startActivity(ShareAppActivity.class);
    }

    //清除缓存
    private void clearCache() {
        showToastMsgShort(getResources().getString(R.string.cache_clearing));
        ImageLoaderHelper.clear(context);
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        if (!isActive()) {
                            return;
                        }
                        tv_cache_size.setText(getCacheSize());
                    }
                });
    }

    //把是否已经登录的标志位设为false
    private void setLoginIsFalse() {
        IMKitHelper.getInstance().getIMKit().getLoginService().logout(null);
        IsLogin isLogin = new IsLogin();
        isLogin.setLogin(false);
        DbHelper.saveValueBySharedPreferences(context, Config.DB.IS_LOGIN_NAME, Config.DB.IS_LOGIN_KEY, isLogin);
    }

    //退出登录
    private void exitLogin() {
        setLoginIsFalse();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("from", 2);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public void checkVersion() {
        showToastMsgShort(getResources().getString(R.string.update_checking));
        String imei = PhoneUtils.getIMEI(context); //IMEI码
        String imsi = PhoneUtils.getIMSI(context); //IMSI码
        String sysinfo = PhoneUtils.getPhoneVersion(); //手机版本号
        String ua = PhoneUtils.getPhoneModel(); //手机型号
        String phonum = PhoneUtils.getLine1Number(context); //line1number
        String v = AppUtils.getAppVersionName(context); //app的版本号
        Map<String, String> map = new HashMap<>();
        map.put("imei", imei);
        map.put("imsi", imsi);
        map.put("sysinfo", sysinfo);
        map.put("ua", ua);
        map.put("phonum", phonum);
        map.put("v", v);
        map.put("account", "");
        presenter.checkVersion(map);
    }

    @Override
    public void createAppSettingDialog() {
        showAppSettingDialog();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void showData(VersionInfo data) {
        if (presenter.checkShouldUpdate(data)) {
            iv_check_version.setVisibility(View.VISIBLE);
            int updateType;
            if (presenter.checkShouldUpdateForced(data)) {
                updateType = 1;
            } else {
                updateType = 2;
            }
            showVersionDialog(data, updateType);
        } else {
            showErrorMsg(getResources().getString(R.string.is_last_version));
        }
        if(data!=null){
            String url=data.getUrl();
            if(!TextUtils.isEmpty(url)){
                DbHelper.saveValueBySharedPreferences(context, Config.DB.ShareAppUrl_NAME,Config.DB.ShareAppUrl_KEY,
                        url);
            }
        }
    }

    @Override
    public void showVersionDialog(VersionInfo versionInfo, int updateType) {
        SettingDialogFragment fragment = SettingDialogFragment.newInstance(versionInfo, updateType);
        fragment.setOnUpdateClickListener(this);
        fragment.show(getChildFragmentManager(), SET_DIALOG_FRAGMENT);
    }

    @Override
    public String getCacheSize() {
        return ImageLoaderHelper.getCacheSize(context);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
        /*
         *  版本校验授权成功
         */
        case VERSION_CHECK_REQUEST_CODE:
            checkVersion();
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
    public void onUpdateClick(String url) {
        Intent intent = new Intent(context, VersionUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startService(intent);
    }
}
