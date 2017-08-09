package com.zfsoftmh.ui.modules.personal_affairs.integral_mall;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/24
 * @Description: 积分商城
 */

public class IntegralMallHomeFragment extends BaseFragment {
    private List<String> listTitle = new ArrayList<>();
    private TabLayout tabLayout;


    public static IntegralMallHomeFragment newInstance() {
        return new IntegralMallHomeFragment();
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        listTitle.add(getResources().getString(R.string.exchange_record_goods_title));
        listTitle.add(getResources().getString(R.string.integral_income_title));
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_integral_mall_home;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_integral_mall_tab);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_integral_mall_view_pager);
        initTabLayout(tabLayout);
        initViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    //初始化viewpager
    private void initViewPager(ViewPager viewPager) {
        IntegralMallHomeAdapter adapter = new IntegralMallHomeAdapter(getChildFragmentManager(), listTitle);
        viewPager.setAdapter(adapter);
    }

    //初始化tabLayout
    private void initTabLayout(TabLayout tabLayout) {

        int size = listTitle.size();
        for (int i = 0; i < size; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(i)));
        }
    }

    @Override
    protected void initListener() {

    }

}
