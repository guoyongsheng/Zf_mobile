package com.zfsoftmh.ui.modules.personal_affairs.contacts.phone_contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017/4/11
 * @Description: 手机通讯录界面
 */

public class PhoneContactsActivity extends BaseActivity {

    private ArrayList<ContactsItemInfo> list;
    private FragmentManager manager;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.phone_contacts);
        setDisplayHomeAsUpEnabled(true);

        PhoneContactsFragment fragment = (PhoneContactsFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = PhoneContactsFragment.newInstance(list);
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
