package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ljq
 * on 2017/7/20.
 */

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    FragmentManager manager;
    AddAddressFragment fragment;
    TextView tv_subtitle;
    UserAddressInfo info;

    @Inject
    AddAddressPresenter presenter;


    @Override
    protected void initVariables() {
        manager=getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        if(bundle!=null){
            info= bundle.getParcelable("address");
        }

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if(info==null){
            setToolbarTitle("添加收货地址");
        }else{
            setToolbarTitle("修改地址");
        }

        setDisplayHomeAsUpEnabled(true);
        tv_subtitle= (TextView) findViewById(R.id.common_subtitle);
        tv_subtitle.setText("保存");
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setOnClickListener(this);
        fragment= (AddAddressFragment) manager.findFragmentById(R.id.common_content);
        if(fragment==null){
                fragment=AddAddressFragment.newInstance(info);
            ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerAddAddressComponent.builder()
        .appComponent(getAppComponent())
        .addAddressModule(new AddAddressModule(fragment))
        .build()
        .inject(this);



    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.common_subtitle){
            if(info==null){
                fragment.check(0);
            }else{
                fragment.check(1);
            }

        }
    }
}
