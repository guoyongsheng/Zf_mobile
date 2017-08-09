package com.zfsoftmh.ui.modules.personal_affairs.school_weibo;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.call.WeiboNotInstalledException;
import com.sina.weibo.sdk.call.WeiboPageUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 学校微博界面
 */

public class SchoolWeiBoActivity extends BaseActivity {

    private static final String TAG = "SchoolWeiBoActivity";
    private static final String UID = "1916655407";

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

        if (!checkIsInstall()) {
            showToastMsgShort(getResources().getString(R.string.sina_not_install));
            finish();
            return;
        }

        try {
            WeiboPageUtils.viewUserInfo(this, UID, null, null);
            finish();
        } catch (WeiboNotInstalledException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " initViews " + e.getMessage());
            finish();
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

    //检测客户端是否安装
    private boolean checkIsInstall() {
        return UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.SINA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
