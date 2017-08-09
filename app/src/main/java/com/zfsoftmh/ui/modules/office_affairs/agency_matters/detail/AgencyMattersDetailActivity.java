package com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.AgencyMattersItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 待办事宜 已办事宜 办结事宜的详情页
 */

public class AgencyMattersDetailActivity extends BaseActivity {

    private int currentItem; //当前的Tab
    private AgencyMattersItemInfo agencyMattersInfo;
    private AgencyMattersDetailFragment fragment;
    private FragmentManager manager;


    @Inject
    AgencyMattersDetailPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        currentItem = bundle.getInt("currentItem");
        agencyMattersInfo = bundle.getParcelable("data");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolBarTitle();
        setDisplayHomeAsUpEnabled(true);

        fragment = (AgencyMattersDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = AgencyMattersDetailFragment.newInstance(currentItem, agencyMattersInfo);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerAgencyMattersDetailComponent.builder()
                .appComponent(getAppComponent())
                .agencyMattersDetailPresenterModule(new AgencyMattersDetailPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    //设置Toolbar的标题
    private void setToolBarTitle() {

        switch (currentItem) {
        /*
         *  待办事宜
         */
        case 0:
            setToolbarTitle(R.string.agency_matters_detail);
            break;

        /*
         *  已办事宜
         */
        case 1:
            setToolbarTitle(R.string.has_been_done_detail);
            break;

        /*
         * 办结事宜
         */
        case 2:
            setToolbarTitle(R.string.settlement_detail);
            break;

        default:
            break;
        }
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }
}
