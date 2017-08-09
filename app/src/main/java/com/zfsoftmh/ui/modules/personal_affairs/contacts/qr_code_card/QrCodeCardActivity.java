package com.zfsoftmh.ui.modules.personal_affairs.contacts.qr_code_card;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 二维码名片界面
 */

public class QrCodeCardActivity extends BaseActivity {

    private ContactsItemInfo info;
    private FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        info = bundle.getParcelable("info");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.qr_code_card);
        setDisplayHomeAsUpEnabled(true);

        QrCodeCardFragment fragment = (QrCodeCardFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = QrCodeCardFragment.newInstance(info);
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
        finish();
    }
}
