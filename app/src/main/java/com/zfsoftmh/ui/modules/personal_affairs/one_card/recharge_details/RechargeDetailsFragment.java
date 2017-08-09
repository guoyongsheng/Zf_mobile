package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: ui 充值明細
 */

public class RechargeDetailsFragment extends BaseListFragment<RechargeDetailsPresenter, OneCardItemDetailsInfo> implements
        RechargeDetailsContract.View {

    private String oneCardId; //一卡通的id

    @Override
    protected RecyclerArrayAdapter<OneCardItemDetailsInfo> getAdapter() {
        return new RechargeDetailsAdapter(context);
    }

    @Override
    protected void loadData() {
        presenter.loadData(start_page, PAGE_SIZE, oneCardId);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        oneCardId = bundle.getString("id");
    }

    public static RechargeDetailsFragment newInstance(String oneCardId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", oneCardId);
        RechargeDetailsFragment fragment = new RechargeDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
