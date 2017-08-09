package com.zfsoftmh.ui.modules.personal_affairs.school_eatery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerModule;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressManagerPresenter;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.SchoolEateryFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.SchoolEateryModule;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.SchoolEateryPresenter;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.OrderFormFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.OrderFormPresenter;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.OrderFromModule;

import java.util.ArrayList;

import javax.inject.Inject;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by ljq on 2017/7/19.
 */

public class SchoolEateryActivity extends BaseActivity implements NavigationTabBar.OnTabBarSelectedIndexListener, View.OnClickListener {

    private TextView tv_subtitle; //标题
    private NavigationTabBar navigationTabBar; //底部导航栏
    private FragmentManager manager;
    private CoordinatorLayout.LayoutParams params;

    OrderFormFragment orderFormFragment;
    SchoolEateryFragment shoolEateryFragment;
    AddressManagerFragment addressManagerFragment;

    public final static String ORDERFORM = "orderFormFragment";
    public final static String SCHOOLEATERY = "schoolEateryFragment";
    public final static String ADDRESSMANAGER = "addressManagerFragment";

    private int currentItem = 0;


    @Inject
    AddressManagerPresenter addressManagerPresenter;

    @Inject
    SchoolEateryPresenter eateryPresenter;

    @Inject
    OrderFormPresenter orderFormPresenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
        params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT);
        AppBarLayout.ScrollingViewBehavior behavior = new AppBarLayout.ScrollingViewBehavior();
        params.setBehavior(behavior);
        params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_navigation_view_height);
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_schooleatery;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_subtitle = (TextView) findViewById(R.id.common_subtitle);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.mess_navigation_tab_bar);
        initNavigationTabBar();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.common_content);
        frameLayout.setLayoutParams(params);
        // tv_title.setVisibility(View.VISIBLE);
        setToolbarTitle("移动食堂");
        setDisplayHomeAsUpEnabled(true);


        addressManagerFragment = AddressManagerFragment.newInstance(false);
        shoolEateryFragment = SchoolEateryFragment.newInstance();
        orderFormFragment = OrderFormFragment.newInstance();
        showFragment(currentItem);
    }


    @Override
    protected void setUpInject() {
        DaggerSchoolEateryComponent.builder()
                .appComponent(getAppComponent())
                .schoolEateryModule(new SchoolEateryModule(shoolEateryFragment))
                .addressManagerModule(new AddressManagerModule(addressManagerFragment))
                .orderFromModule(new OrderFromModule(orderFormFragment))
                .build()
                .inject(this);

    }

    @Override
    protected void initListener() {
        tv_subtitle.setOnClickListener(this);
        navigationTabBar.setOnTabBarSelectedIndexListener(this);

    }

    private void initNavigationTabBar() {
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        int color = ContextCompat.getColor(this, R.color.colorWhite);

        //首页
        Drawable drawableHome = ContextCompat.getDrawable(this, R.mipmap.ic_home_white_24dp);
        NavigationTabBar.Model modelHome = new NavigationTabBar.Model.Builder(drawableHome, color)
                .title(getResources().getString(R.string.home_page))
                .build();
        models.add(modelHome);

        //订单
        Drawable drableInterestCircle = ContextCompat.getDrawable(this, R.mipmap.ic_favorite_red_400_24dp);
        NavigationTabBar.Model modelInterestCircle = new NavigationTabBar.Model.Builder(drableInterestCircle, color)
                .title(getResources().getString(R.string.order_form))
                .build();
        models.add(modelInterestCircle);

        //订单管理
        Drawable drawableAppCenter = ContextCompat.getDrawable(this, R.mipmap.ic_apps_white_24dp);
        NavigationTabBar.Model modelAppCenter = new NavigationTabBar.Model.Builder(drawableAppCenter, color)
                .title(getResources().getString(R.string.address_manager))
                .build();
        models.add(modelAppCenter);

        navigationTabBar.setModels(models);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setIsBadged(false);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }


    private void showFragment(int currentItem) {
        navigationTabBar.setModelIndex(currentItem);
        FragmentTransaction transaction = manager.beginTransaction();
        switch (currentItem) {
            case 0:
                setToolbarTitle("首页");
                tv_subtitle.setVisibility(View.GONE);
                if (shoolEateryFragment != null && !shoolEateryFragment.isAdded())
                    ActivityUtils.addFragmentToActivityWithTag(manager, shoolEateryFragment, R.id.common_content, SCHOOLEATERY);
                transaction.show(shoolEateryFragment);
                if (addressManagerFragment.getUserVisibleHint())
                    transaction.hide(addressManagerFragment);
                if (orderFormFragment.getUserVisibleHint())
                    transaction.hide(orderFormFragment);
                transaction.commit();
                break;
            case 1:
                setToolbarTitle("订单");
                tv_subtitle.setVisibility(View.GONE);
                if (orderFormFragment != null && !orderFormFragment.isAdded()) {
                    ActivityUtils.addFragmentToActivityWithTag(manager, orderFormFragment, R.id.common_content, ORDERFORM);
                }

                transaction.show(orderFormFragment).hide(addressManagerFragment).hide(shoolEateryFragment).commit();
                break;
            case 2:
                setToolbarTitle("管理地址");
                tv_subtitle.setText("管理");
                tv_subtitle.setVisibility(View.VISIBLE);
                if (addressManagerFragment != null && !addressManagerFragment.isAdded()) {
                    ActivityUtils.addFragmentToActivityWithTag(manager, addressManagerFragment, R.id.common_content, ADDRESSMANAGER);
                }
                transaction.show(addressManagerFragment).hide(shoolEateryFragment).hide(orderFormFragment).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTabSelected(NavigationTabBar.Model model, int index) {

    }

    @Override
    public void onEndTabSelected(NavigationTabBar.Model model, int index) {
        switch (index) {
            case 0:
                if (currentItem == 0) {
                    return;
                }
                currentItem = 0;
                showFragment(currentItem);
                break;
            case 1:
                if (currentItem == 1) {
                    return;
                }
                currentItem = 1;
                showFragment(currentItem);
                break;
            case 2:
                if (currentItem == 2) {
                    return;
                }
                currentItem = 2;
                showFragment(currentItem);
                break;
            default:
                break;
        }
    }

    private void newInstanceAgain() {
        if (shoolEateryFragment == null) {
            shoolEateryFragment = SchoolEateryFragment.newInstance();
        }

//
//    private void newInstanceAgain(){
//        if(shoolEateryFragment==null){
//            shoolEateryFragment=ShoolEateryFragment.newInstance();
//        }
//
//        if(orderFormFragment==null){
//            orderFormFragment=OrderFormFragment.newInstance();
//        }
//
//        if(addressManagerFragment==null){
//            addressManagerFragment=AddressManagerFragment.newInstance(false);
//        }
//    }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_subtitle) {
               addressManagerFragment.setIschoice(true);
          }
    }
}