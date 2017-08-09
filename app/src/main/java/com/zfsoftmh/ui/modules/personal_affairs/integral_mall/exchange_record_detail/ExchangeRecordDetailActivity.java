package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/11
 * @Description:
 */

public class ExchangeRecordDetailActivity extends BaseActivity {
    private ExchangeRecordItemInfo info;
    private FragmentManager manager;
    private ExchangeRecordDetailFragment fragment;

    @Inject
    ExchangeRecordDetailPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        info = bundle.getParcelable("ExchangeRecordFragment");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.exchange_detail_title);
        setDisplayHomeAsUpEnabled(true);

        fragment = (ExchangeRecordDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = ExchangeRecordDetailFragment.newInstance(info);
            ActivityUtils.addFragmentToActivity(manager,fragment,R.id.common_content);
        }
    }


    @Override
    protected void setUpInject() {
        DaggerExchangeRecordDetailComponent.builder()
                .appComponent(getAppComponent())
                .exchangeRecordDetailPresenterModule(new ExchangeRecordDetailPresenterModule(fragment))
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
}
