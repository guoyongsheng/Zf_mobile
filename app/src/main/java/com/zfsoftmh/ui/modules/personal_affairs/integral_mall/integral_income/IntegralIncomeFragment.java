package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.IntegralIncomeItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 积分明细
 */

public class IntegralIncomeFragment extends BaseListFragment<IntegralIncomePresenter, IntegralIncomeItemInfo>
        implements IntegralIncomeContract.View {
    private IntegralIncomeAdapter adapter;

    public static IntegralIncomeFragment newInstance() {
        return new IntegralIncomeFragment();
    }

    @Override
    protected RecyclerArrayAdapter<IntegralIncomeItemInfo> getAdapter() {
        adapter = new IntegralIncomeAdapter(context);
        return adapter;
    }

    @Override
    protected void loadData() {
        getIntegralIncomeList(start_page, PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void createLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideUpLoadingDialog() {
        hideProgressDialog();
    }



    @Override
    public void getIntegralIncomeList(int start_page, int PAGE_SIZE) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(start_page));
        params.put("size", String.valueOf(PAGE_SIZE));
        presenter.getIntegralIncome(params);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }
}
