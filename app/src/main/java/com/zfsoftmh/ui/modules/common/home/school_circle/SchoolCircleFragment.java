package com.zfsoftmh.ui.modules.common.home.school_circle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImmersionStatusBarUtils;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 校园圈ui
 */

public class SchoolCircleFragment extends BaseFragment {

    private List<String> listTitle = new ArrayList<>();
    private TabLayout tabLayout;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        listTitle.add(getResources().getString(R.string.school_dynamics));
        listTitle.add(getResources().getString(R.string.my_attention));
        listTitle.add(getResources().getString(R.string.interest_circle));
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_school_circle;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_school_circle_tab);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_school_circle_view_pager);

        initTabLayout(tabLayout);
        initViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }

    //初始化viewpager
    private void initViewPager(ViewPager viewPager) {
        SchoolCircleAdapter adapter = new SchoolCircleAdapter(getChildFragmentManager(), listTitle);
        viewPager.setAdapter(adapter);
    }

    //初始化tabLayout
    private void initTabLayout(TabLayout tabLayout) {

        int size = listTitle.size();
        for (int i = 0; i < size; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(i)));
        }
    }

    public static SchoolCircleFragment newInstance() {
        return new SchoolCircleFragment();
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }

    @Override
    protected void immersionInit() {
        super.immersionInit();

        ImmersionStatusBarUtils.initStatusBarInFragment(this, R.color.colorPrimary,
                R.color.colorPrimary, tabLayout, true, 0.2f, immersionBar);
    }
}
