package com.zfsoftmh.ui.modules.personal_affairs.contacts.contacts_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 通讯录详情
 */

public class ContactsDetailActivity extends BaseActivity {

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
        return R.layout.activity_contacts_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("");
        setDisplayHomeAsUpEnabled(true);

        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.contacts_detail_image);
        TextView tv_name = (TextView) findViewById(R.id.contacts_detail_name);

        ContactsDetailFragment fragment = (ContactsDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ContactsDetailFragment.newInstance(info);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }

        ImageLoaderHelper.loadImage(this, circleImageView, info.getPhotoUri());
        tv_name.setText(info.getName());
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
