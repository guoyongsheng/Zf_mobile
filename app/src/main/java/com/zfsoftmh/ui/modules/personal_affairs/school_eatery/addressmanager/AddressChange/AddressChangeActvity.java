package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressChange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerModule;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerPresenter;

import javax.inject.Inject;

/**
 * Created by li
 * on 2017/8/2.
 */

public class AddressChangeActvity extends BaseActivity {

    FragmentManager manager;
    AddressManagerFragment fragment;
    TextView common_subtitle;

    @Inject
    AddressManagerPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
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
        setToolbarTitle("切换地址");
        setDisplayHomeAsUpEnabled(true);
        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setText("确定");
        common_subtitle.setVisibility(View.VISIBLE);

        fragment = (AddressManagerFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = AddressManagerFragment.newInstance(true);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }

    }

    @Override
    protected void setUpInject() {
        DaggerAddressChangeComponent.builder()
                .appComponent(getAppComponent())
                .addressManagerModule(new AddressManagerModule(fragment))
                .build()
                .inject(this);

    }

    @Override
    protected void initListener() {
        common_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.finish();
            }
        });
    }
}
