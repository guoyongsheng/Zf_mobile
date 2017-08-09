package com.zfsoftmh.ui.modules.personal_affairs.integral_mall;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income.IntegralIncomeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking.IntegralRankingActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/24
 * @Description: 积分商城首页
 */

public class IntegralMallHomeActivity extends BaseActivity {
    private FragmentManager manager;
    private String integralNumber; // 积分数
    private TextView integral_number; // 积分
    private List<String> listTitle = new ArrayList<>();
    private TabLayout tabLayout;
    private LinearLayout integral_mall_home_top; // 积分商城头布局
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private LinearLayout integral_income; // 积分明细
    private LinearLayout points_ranking_layout; // 积分排名

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
        listTitle.add(getResources().getString(R.string.exchange_record_goods_title));
        listTitle.add(getResources().getString(R.string.exchange_record_title));
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    private int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId) + res.getDimensionPixelSize(R.dimen.bottom_navigation_view_height);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIntegral();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_integral_mall_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.integral_mall_title);
        setDisplayHomeAsUpEnabled(true);
        // 头部布局
        integral_mall_home_top = (LinearLayout) findViewById(R.id.integral_mall_home_top);
        CollapsingToolbarLayout.LayoutParams lp = new CollapsingToolbarLayout.LayoutParams(integral_mall_home_top.getLayoutParams());
        lp.setMargins(0, getInternalDimensionSize(getResources(), STATUS_BAR_HEIGHT_RES_NAME), 0, 0);
        integral_mall_home_top.setLayoutParams(lp);
        // 积分
        integral_number = (TextView) findViewById(R.id.integral_number);

        // 积分明细
        integral_income = (LinearLayout) findViewById(R.id.integral_income);
        integral_income.setOnClickListener(onceClickListener);
        // 积分排名
        points_ranking_layout = (LinearLayout) findViewById(R.id.points_ranking_layout);
        points_ranking_layout.setOnClickListener(onceClickListener);
        // 积分兑换以及兑换记录
        tabLayout = (TabLayout) findViewById(R.id.fragment_integral_mall_tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_integral_mall_view_pager);
        initTabLayout(tabLayout);
        initViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }


    //初始化viewpager
    private void initViewPager(ViewPager viewPager) {
        IntegralMallHomeAdapter adapter = new IntegralMallHomeAdapter(manager, listTitle);
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

    /**
     * 设置积分
     */
    public void getIntegral() {
        integralNumber = SharedPreferenceUtils.readString(this, "integral", "integral");

        // 设置积分数
        if (integralNumber != null)
            integral_number.setText(integralNumber);
    }


    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        immersionBar.transparentStatusBar().init();
    }

    private OnceClickListener onceClickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            int id = v.getId();
            switch (id) {
                /**
                 * 积分明细
                 */
                case R.id.integral_income:
                    startActivity(IntegralIncomeActivity.class);
                    break;
                /**
                 * 积分排名
                 */
                case R.id.points_ranking_layout:
                    startActivity(IntegralRankingActivity.class);
                    break;
            }
        }
    };
}
