package com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail;

import android.os.Bundle;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.AgencyMattersItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 详情页ui
 */

public class AgencyMattersDetailFragment extends BaseFragment<AgencyMattersDetailPresenter>
        implements AgencyMattersDetailContract.View {

    private int currentItem;  //当前的Tab页
    private AgencyMattersItemInfo agencyMattersInfo;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        currentItem = bundle.getInt("currentItem");
        agencyMattersInfo = bundle.getParcelable("data");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_agency_matters_detail;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initListener() {

    }

    public static AgencyMattersDetailFragment newInstance(int currentItem, AgencyMattersItemInfo
            agencyMattersInfo) {

        AgencyMattersDetailFragment fragment = new AgencyMattersDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currentItem", currentItem);
        bundle.putParcelable("data", agencyMattersInfo);
        fragment.setArguments(bundle);
        return fragment;
    }
}
