package com.zfsoftmh.ui.modules.common.home;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.config.ServiceCodeConfig;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.MyPortalItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.common.home.find.FindFragment;
import com.zfsoftmh.ui.modules.common.home.find.FindPresenter;
import com.zfsoftmh.ui.modules.common.home.find.FindPresenterModule;
import com.zfsoftmh.ui.modules.common.home.home.HomeFragment;
import com.zfsoftmh.ui.modules.common.home.home.HomePresenter;
import com.zfsoftmh.ui.modules.common.home.home.HomePresenterModule;
import com.zfsoftmh.ui.modules.common.home.message.MessageFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MineFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MinePresenter;
import com.zfsoftmh.ui.modules.common.home.mine.MinePresenterModule;
import com.zfsoftmh.ui.modules.common.home.school_circle.SchoolCircleFragment;
import com.zfsoftmh.ui.modules.common.login.LoginActivity;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.AgencyMattersActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import devlight.io.library.ntb.NavigationTabBar;


/**
 * 把版本校验的接口写在了MainFragment中
 * 第一个原因: MainActivity没有Presenter层的引用
 * 第二个原因: MainFragment是MainActivity默认显示的,也只会被创建一次, 版本校验的接口也不会重复调用
 *
 * @author wesley
 * @date: 2017/3/14
 * @Description: 首页
 */
public class HomeActivity extends BaseActivity implements HomeFragment.OnItemClickListener,
        MineFragment.OnItemClickListener, NavigationTabBar.OnTabBarSelectedIndexListener {

    private static final String TAG_MAIN_FRAGMENT = "HomeFragment";
    private static final String TAG_FIND_FRAGMENT = "FindFragment";
    private static final String TAG_MINE_FRAGMENT = "MineFragment";
    private static final String TAG_MESSAGE_FRAGMENT = "messageFragment";
    private static final String TAG_SCHOOL_CIRCLE_FRAGMENT = "SchoolCircleFragment";

    private NavigationTabBar navigationTabBar; //底部导航栏
    private HomeFragment homeFragment; //首页的Fragment
    private SchoolCircleFragment schoolCircleFragment; //校园圈fragment
    private FindFragment findFragment; //发现fragment
    private MessageFragment messageFragment; //消息的fragment
    private MineFragment mineFragment; //我的Fragment
    private FragmentManager manager;
    private CoordinatorLayout.LayoutParams params;
    private int currentItem = 0; //当前的tab
    private boolean isBackFirstClick = true; //是不是第一次点击返回键
    private long firstClickTime; //第一次点击返回键时的时间
    private float fontSize = 1; // 设置字体大小

    @Inject
    HomePresenter homePresenter;

    @Inject
    FindPresenter findPresenter;

    @Inject
    MinePresenter minePresenter;

    @Override
    protected void initVariables() {
        fontSize = SharedPreferenceUtils.readFloat(HomeActivity.this, "fontSize", "fontSize");
        getResources();
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
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        navigationTabBar = (NavigationTabBar) findViewById(R.id.main_bottom_navigation_tab_bar);
        initNavigationTabBar();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.common_content);
        frameLayout.setLayoutParams(params);

        if (savedInstanceState != null) {
            currentItem = savedInstanceState.getInt("currentItem");
            homeFragment = (HomeFragment) manager.findFragmentByTag(TAG_MAIN_FRAGMENT);
            schoolCircleFragment = (SchoolCircleFragment) manager.findFragmentByTag(TAG_SCHOOL_CIRCLE_FRAGMENT);
            findFragment = (FindFragment) manager.findFragmentByTag(TAG_FIND_FRAGMENT);
            mineFragment = (MineFragment) manager.findFragmentByTag(TAG_MINE_FRAGMENT);
            messageFragment = (MessageFragment) manager.findFragmentByTag(TAG_MESSAGE_FRAGMENT);
            newInstanceAgain();
        } else {
            homeFragment = HomeFragment.newInstance();
            schoolCircleFragment = SchoolCircleFragment.newInstance();
            findFragment = FindFragment.newInstance();
            mineFragment = MineFragment.newInstance();
            messageFragment = MessageFragment.newInstance();
        }
        homeFragment.setOnItemClickListener(this);
        mineFragment.setOnItemClickListener(this);
        showFragment(currentItem);
    }

    @Override
    protected void setUpInject() {
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homePresenterModule(new HomePresenterModule(homeFragment))
                .findPresenterModule(new FindPresenterModule(findFragment))
                .minePresenterModule(new MinePresenterModule(mineFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        navigationTabBar.setOnTabBarSelectedIndexListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentItem", currentItem);
    }

    //初始化底部导航栏
    private void initNavigationTabBar() {
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        int color = ContextCompat.getColor(this, R.color.colorWhite);

        //首页
        Drawable drawableHome = ContextCompat.getDrawable(this, R.mipmap.home_new);
        NavigationTabBar.Model modelHome = new NavigationTabBar.Model.Builder(drawableHome, color)
                .title(getResources().getString(R.string.home_page))
                .build();
        models.add(modelHome);

        //校园圈
        Drawable drableInterestCircle = ContextCompat.getDrawable(this, R.mipmap.circle_new);
        NavigationTabBar.Model modelInterestCircle = new NavigationTabBar.Model.Builder(drableInterestCircle, color)
                .title(getResources().getString(R.string.school_circle))
                .build();
        models.add(modelInterestCircle);

        //发现
        Drawable drawableAppCenter = ContextCompat.getDrawable(this, R.mipmap.find_new);
        NavigationTabBar.Model modelAppCenter = new NavigationTabBar.Model.Builder(drawableAppCenter, color)
                .title(getResources().getString(R.string.find))
                .build();
        models.add(modelAppCenter);

        //消息
        Drawable drawableMessage = ContextCompat.getDrawable(this, R.mipmap.message_new);
        final NavigationTabBar.Model modelMessage = new NavigationTabBar.Model.Builder(drawableMessage, color)
                .title(getResources().getString(R.string.message))
                .build();
        models.add(modelMessage);

        //我的
        Drawable drawableMine = ContextCompat.getDrawable(this, R.mipmap.mine_new);
        NavigationTabBar.Model modelMine = new NavigationTabBar.Model.Builder(drawableMine, color)
                .title(getResources().getString(R.string.mine))
                .build();
        models.add(modelMine);

        navigationTabBar.setModels(models);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setIsBadged(false);
    }

    //确保fragment不能为空
    private void newInstanceAgain() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }

        if (schoolCircleFragment == null) {
            schoolCircleFragment = SchoolCircleFragment.newInstance();
        }

        if (findFragment == null) {
            findFragment = FindFragment.newInstance();
        }

        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
        }

        if (messageFragment == null) {
            messageFragment = MessageFragment.newInstance();
        }
    }

    //显示Fragment
    private void showFragment(int currentItem) {
        navigationTabBar.setModelIndex(currentItem);
        FragmentTransaction transaction = manager.beginTransaction();
        switch (currentItem) {
        /*
         * 显示MainFragment
         */
        case 0:
            if (homeFragment != null && !homeFragment.isAdded()) {
                ActivityUtils.addFragmentToActivityWithTag(manager, homeFragment, R.id.common_content, TAG_MAIN_FRAGMENT);
            }
            transaction.show(homeFragment).hide(schoolCircleFragment).hide(messageFragment).hide(findFragment).hide(mineFragment);
            break;

        /*
         * 显示SchoolCircleFragment
         */
        case 1:
            if (schoolCircleFragment != null && !schoolCircleFragment.isAdded()) {
                ActivityUtils.addFragmentToActivityWithTag(manager, schoolCircleFragment, R.id.common_content, TAG_SCHOOL_CIRCLE_FRAGMENT);
            }
            transaction.show(schoolCircleFragment).hide(homeFragment).hide(messageFragment).hide(findFragment).hide(mineFragment);
            break;

        /*
         * 显示FindFragment
         */
        case 2:
            if (findFragment != null && !findFragment.isAdded()) {
                ActivityUtils.addFragmentToActivityWithTag(manager, findFragment, R.id.common_content, TAG_FIND_FRAGMENT);
            }
            transaction.show(findFragment).hide(messageFragment).hide(homeFragment).hide(mineFragment).hide(schoolCircleFragment);
            break;

        /*
         * 消息
         */
        case 3:
            if (messageFragment != null && !messageFragment.isAdded()) {
                ActivityUtils.addFragmentToActivityWithTag(manager, messageFragment, R.id.common_content, TAG_MESSAGE_FRAGMENT);
            }
            transaction.show(messageFragment).hide(homeFragment).hide(findFragment).hide(mineFragment).hide(schoolCircleFragment);
            break;

        /*
         * 显示MineFragment
         */
        case 4:
            if (mineFragment != null && !mineFragment.isAdded()) {
                ActivityUtils.addFragmentToActivityWithTag(manager, mineFragment, R.id.common_content, TAG_MINE_FRAGMENT);
            }
            transaction.show(mineFragment).hide(messageFragment).hide(homeFragment).hide(findFragment).hide(schoolCircleFragment);
            break;

        default:
            break;
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentItem != 0) {
            currentItem = 0;
            showFragment(currentItem);
        } else {
            long currentClickTime = System.currentTimeMillis();
            if (isBackFirstClick) {
                firstClickTime = System.currentTimeMillis();
                isBackFirstClick = false;
                showToastMsgShort(getResources().getString(R.string.click_again_will_exit));
            } else {
                if (currentClickTime - firstClickTime <= Config.INTERVAL_TIME_ON_CLICK) {
                    super.onBackPressed();
                } else {
                    firstClickTime = currentClickTime;
                    showToastMsgShort(getResources().getString(R.string.click_again_will_exit));
                }
            }
        }
    }

    //判断用户有没有登录
    private boolean checkUserIsLogin() {
        return DbHelper.checkUserIsLogin(this);
    }

    //跳转到登录界面
    private void openActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AppCenterItemInfo itemInfo) {
        if (itemInfo == null) {
            return;
        }
        String type = itemInfo.getType(); //服务类型
        if (type != null && type.equals(Constant.APP_APPLICATION)) {
            dealWithAppApplication(itemInfo);
        } else if (type != null && type.equals(Constant.APP_SERVICE)) {
            dealWithLocalService(itemInfo);
        } else if (type != null && type.equals(Constant.WEB_SERVICE)) {
            dealWithWebService(itemInfo);
        }
    }

    //处理application
    private void dealWithAppApplication(AppCenterItemInfo itemInfo) {
        LoggerHelper.e(TAG_FIND_FRAGMENT, itemInfo.getId());
    }

    //处理本地服务
    private void dealWithLocalService(AppCenterItemInfo itemInfo) {
        String serviceCode = itemInfo.getServiceCode(); //服务编码
        Class<?> activity = ServiceCodeConfig.getActivityByServiceCode(serviceCode);
        if (activity == null) {
            showToastMsgShort(getResources().getString(R.string.not_know_activity));
            return;
        }
        Intent intent = new Intent(this, activity);
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", itemInfo);
        if (activity.equals(AgencyMattersActivity.class)) {
            if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_AGENCY_MATTERS))) {
                bundle.putInt("currentItem", 0);
            } else if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_HAS_BEEN_DONE))) {
                bundle.putInt("currentItem", 1);
            } else if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_SETTLEMENT))) {
                bundle.putInt("currentItem", 2);
            }
        }
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //处理网页服务
    private void dealWithWebService(AppCenterItemInfo itemInfo) {
        String otherFlag = itemInfo.getOtherFlag();
        if (checkIsOtherFlag(otherFlag)) {
            openWebActivity(itemInfo.getName(), itemInfo.getUrl(), itemInfo.getProcode());
        } else {
            openWebActivity(itemInfo.getName(), itemInfo.getUrl());
        }
    }

    //打开web界面
    private void openWebActivity(String title, String url) {
        Intent intent = new Intent(this, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, url);
        bundle.putString(Config.WEB.TITLE, title);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //打开web界面
    private void openWebActivity(String title, String url, String proCode) {
        Intent intent = new Intent(this, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, url);
        bundle.putString(Config.WEB.TITLE, title);
        bundle.putString(Config.WEB.PRO_CODE, proCode);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //判断是否是otherFlag
    private boolean checkIsOtherFlag(String otherFlag) {
        return otherFlag != null && otherFlag.equals("0");
    }

    @Override
    public void onItemClick(MyPortalItemInfo itemInfo) {
        if (itemInfo == null) {
            return;
        }
        String type = itemInfo.getServiceType(); //服务类型
        if (type != null && type.equals(Constant.APP_SERVICE)) {
            String serviceCode = itemInfo.getServiceCode(); //服务编码
            String name = itemInfo.getName(); //名称
            AppCenterItemInfo appCenterItemInfo = new AppCenterItemInfo();
            appCenterItemInfo.setServiceCode(serviceCode);
            appCenterItemInfo.setName(name);
            dealWithLocalService(appCenterItemInfo);
        } else if (type != null && type.equals(Constant.WEB_SERVICE)) {
            openWebActivity(itemInfo.getName(), itemInfo.getUrl());
        }
    }

    @Override
    public void onStartTabSelected(NavigationTabBar.Model model, int index) {

    }

    @Override
    public void onEndTabSelected(NavigationTabBar.Model model, int index) {

        switch (index) {
        /*
         * 首页
         */
        case 0:
            if (currentItem == 0) {
                return;
            }
            currentItem = 0;
            showFragment(currentItem);
            break;

        /*
         * 兴趣圈
         */
        case 1:
            if (!checkUserIsLogin()) {
                showToastMsgShort(getResources().getString(R.string.user_is_not_login));
                openActivity();
                return;
            }
            if (currentItem == 1) {
                return;
            }
            currentItem = 1;
            showFragment(currentItem);
            break;

        /*
         * 发现
         */
        case 2:
            if (!checkUserIsLogin()) {
                showToastMsgShort(getResources().getString(R.string.user_is_not_login));
                openActivity();
                return;
            }
            if (currentItem == 2) {
                return;
            }
            currentItem = 2;
            showFragment(currentItem);
            break;

        /*
         * 消息
         */
        case 3:
            if (!checkUserIsLogin()) {
                showToastMsgShort(getResources().getString(R.string.user_is_not_login));
                openActivity();
                return;
            }
            if (currentItem == 3) {
                return;
            }
            currentItem = 3;
            showFragment(currentItem);
            break;

        /*
         *  我的
         */
        case 4:
            if (!checkUserIsLogin()) {
                showToastMsgShort(getResources().getString(R.string.user_is_not_login));
                openActivity();
                return;
            }
            if (currentItem == 4) {
                return;
            }
            currentItem = 4;
            showFragment(currentItem);
            break;

        default:
            break;
        }
    }

    /**
     * 重写Resources方法设置字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        //获取到resources对象
        Resources res = super.getResources();
        //修改configuration的fontScale属性
        res.getConfiguration().fontScale = fontSize;
        //将修改后的值更新到metrics.scaledDensity属性上
        res.updateConfiguration(null, null);
        return res;
    }
}
