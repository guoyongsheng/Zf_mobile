package com.zfsoftmh.ui.modules.common.home.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ImmersionStatusBarUtils;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.PhoneUtils;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.common.utils.TimeUtils;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.BannerInfo;
import com.zfsoftmh.entity.IsNewVersion;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.entity.SemesterWeekInfo;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail.ScheduleDetailActivity;
import com.zfsoftmh.ui.modules.school_portal.subscription_center.SubscriptionCenterActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;
import com.zfsoftmh.ui.service.VersionUpdateService;
import com.zfsoftmh.ui.widget.divider.GridItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 首页的Fragment
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View,
        EasyPermissions.PermissionCallbacks, HomeDialogFragment.OnUpdateClickListener,
        HomeHeaderAdapter.OnItemClickListener, OnBannerListener, AMapLocationListener,
        WeatherSearch.OnWeatherSearchListener, HomeAdapter.OnItemClickListener,
        HomeItemDialogFragment.OnItemClickListener {

    private static final String TAG = "HomeFragment";
    private static final String TAG_VERSION_UPDATE = "TAG_VERSION_UPDATE";
    private static final String TAG_ITEM = "TAG_ITEM";
    private static final int VERSION_CHECK_REQUEST_CODE = 1; //版本校验的权限请求码
    private static final int WEATHER_REQUEST_CODE = 3;//获取天气信息的请求码
    private static final int REQUEST_CODE_SUBSCRIPTION_CENTER = 2; //订阅中心的请求码
    private static final int SPAN_COUNT = 4; //列数

    private Banner banner; //轮播图
    private HomeHeaderAdapter headerAdapter; //头部适配器
    private HomeAdapter adapter; //适配器
    private int itemHeight; //RecyclerView的Item高度
    private List<BannerInfo> bannerInfoList; //banner集合
    private OnItemClickListener listener;
    private AMapLocationClient mLocationClient; //定位客户端

    private LinearLayout ll_home_search;
    private RecyclerView recyclerView;

    @Override
    protected void initVariables() {
        itemHeight = getItemHeight();
        adapter = new HomeAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        ll_home_search = (LinearLayout) view.findViewById(R.id.home_search);
        LinearLayout comments_search = (LinearLayout) view.findViewById(R.id.comments_search);
        comments_search.getBackground().setAlpha(120);//0~255透明度值
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        recyclerView.setAdapter(adapter);
        initHeaderView();
        getSemesterWeekInfo();
        requestPermissions();
        requestPermissionsForLocation();
    }

    //初始化item---日程管理
    private void initItemViewMySchedule() {
        List<ScheduleManagementInfo> list = getScheduleManagementInfoList(System.currentTimeMillis());
        adapter.initMySchedule(list);
    }

    //初始化Banner
    private void initHeaderView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_banner, null);
        adapter.initHeaderView(view);
        initHeaderBanner(view);
        initHeaderRecycler(view);
    }

    //初始化轮播图
    private void initHeaderBanner(View view) {
        ArrayList<String> listImage = new ArrayList<>();
        listImage.add("http://img1.imgtn.bdimg.com/it/u=2343882896,3900438165&fm=26&gp=0.jpg");
        ArrayList<String> listTitle = new ArrayList<>();
        listTitle.add("日向雏田...");
        banner = (Banner) view.findViewById(R.id.home_page_navigation_banner);
        int bannerHeight = ScreenUtils.getScreenWidth(context) * 3 / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bannerHeight);
        banner.setLayoutParams(params);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImages(listImage);
        banner.setBannerTitles(listTitle);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        getHomeBannerInfo();
    }

    //初始化服务
    private void initHeaderRecycler(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        //ItemDecoration
        GridItemDecoration gridItemDecoration = new GridItemDecoration(ContextCompat.getColor(context,
                R.color.common_divider_color));
        recyclerView.addItemDecoration(gridItemDecoration);

        //headerAdapter
        boolean isLogin = checkUserIsLogin();
        headerAdapter = new HomeHeaderAdapter(context, isLogin, itemHeight);
        recyclerView.setAdapter(headerAdapter);
        if (isLogin) {
            getHomeServiceWithLogin();
        } else {
            getHomeServiceWithNoLogin();
        }
    }

    @Override
    protected void initListener() {
        banner.setOnBannerListener(this);
        headerAdapter.setOnItemClickListener(this);
        recyclerView.addOnScrollListener(new OnScrollRecyclerView());
    }

    /**
     * 实例化MainFragment
     *
     * @return MainFragment的对象
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
        /*
         *  版本校验授权成功
         */
        case VERSION_CHECK_REQUEST_CODE:
            checkVersion();
            break;

        /*
         * 获取位置授权成功
         */
        case WEATHER_REQUEST_CODE:
            startLocation();
            break;

        default:
            break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            createAppSettingDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void createAppSettingDialog() {
        showAppSettingDialog();
    }

    @Override
    public void checkVersion() {
        String imei = PhoneUtils.getIMEI(context); //IMEI码
        String imsi = PhoneUtils.getIMSI(context); //IMSI码
        String sysinfo = PhoneUtils.getPhoneVersion(); //手机版本号
        String ua = PhoneUtils.getPhoneModel(); //手机型号
        String phonum = PhoneUtils.getLine1Number(context); //line1number
        String v = AppUtils.getAppVersionName(context); //app的版本号
        Map<String, String> map = new HashMap<>();
        map.put("imei", imei);
        map.put("imsi", imsi);
        map.put("sysinfo", sysinfo);
        map.put("ua", ua);
        map.put("phonum", phonum);
        map.put("v", v);
        map.put("account", "");
        presenter.checkVersion(map);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void showData(VersionInfo data) {
        if (presenter.checkShouldUpdate(data)) {
            int updateType;
            if (presenter.checkShouldUpdateForced(data)) {
                updateType = 1;
            } else {
                updateType = 2;
            }
            showVersionDialog(data, updateType);
        }
    }

    @Override
    public void showVersionDialog(VersionInfo versionInfo, int updateType) {
        HomeDialogFragment dialogFragment = HomeDialogFragment.newInstance(versionInfo, updateType);
        dialogFragment.setOnUpdateClickListener(this);
        dialogFragment.show(getChildFragmentManager(), TAG_VERSION_UPDATE);
    }

    @Override
    public void setNewVersion(boolean isNewVersion) {
        IsNewVersion newVersion = new IsNewVersion();
        newVersion.setNewVersion(isNewVersion);
        DbHelper.saveValueBySharedPreferences(context, Config.DB.IS_NEW_VERSION_NAME,
                Config.DB.IS_NEW_VERSION_KEY, newVersion);
    }

    @Override
    public void requestPermissions() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permissions_phone_state),
                VERSION_CHECK_REQUEST_CODE, Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public boolean checkUserIsLogin() {
        return DbHelper.checkUserIsLogin(context);
    }

    public void getHomeBannerInfo() {
        presenter.getHomeBanner(Config.HOME_MAX_BANNER_SIZE);
    }

    @Override
    public void loadBannerInfoSuccess(List<BannerInfo> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        bannerInfoList = list;
        List<String> titleList = getTitleList(list);
        List<String> imageList = getImageList(list);
        banner.update(imageList, titleList);
    }

    @Override
    public List<String> getTitleList(List<BannerInfo> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            BannerInfo bannerInfo = list.get(i);
            if (bannerInfo != null) {
                titleList.add(bannerInfo.getTitle());
            }
        }
        return titleList;
    }

    @Override
    public List<String> getImageList(List<BannerInfo> list) {
        List<String> imageList = new ArrayList<>();
        if (list == null) {
            return imageList;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            BannerInfo bannerInfo = list.get(i);
            if (bannerInfo != null) {
                List<String> listImage = bannerInfo.getLogoPathList();
                if (listImage != null && listImage.size() > 0) {
                    imageList.add(listImage.get(0));
                }
            }
        }
        return imageList;
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void getHomeServiceWithNoLogin() {
        presenter.getHomeServiceWithNoLogin();
    }

    @Override
    public void getHomeServiceWithLogin() {
        presenter.getHomeServiceWithLogin();
    }

    @Override
    public void loadHomeServiceInfoSuccess(ArrayList<AppCenterItemInfo> list) {
        headerAdapter.setDataSource(list);
    }

    @Override
    public int getItemHeight() {
        int width = ScreenUtils.getScreenWidth(context);
        return (int) (width * (3 / 16.0));
    }

    @Override
    public void openWebViewActivity(String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.TITLE, title);
        bundle.putString(Config.WEB.URL, url);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public List<ScheduleManagementInfo> getScheduleManagementInfoList(long time) {
        List<ScheduleManagementInfo> listResult = new ArrayList<>();
        List<ScheduleManagementInfo> list = getAppComponent().getRealmHelper().queryScheduleByTime(longParseToString(time));
        if (list == null) {
            return listResult;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ScheduleManagementInfo info = list.get(i);
            if (info != null) {
                long startTime = stringParseToLong(info.getStart_time()); //开始时间
                if (startTime >= time) {
                    listResult.add(info);

                    if (listResult.size() == 2) {
                        break;
                    }
                }
            }
        }
        return listResult;
    }

    @Override
    public long stringParseToLong(String startTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(startTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " stringParseToLong " + e.getMessage());
        }
        return 0;
    }

    @Override
    public String longParseToString(long currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(currentTime);
        return format.format(date);
    }

    @Override
    public void requestPermissionsForLocation() {
        EasyPermissions.requestPermissions(this,
                context.getResources().getString(R.string.request_permissions_for_get_weather),
                WEATHER_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    public void startLocation() {
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        AMapLocationClientOption mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mapLocationClientOption.setOnceLocation(true);
        mapLocationClientOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mapLocationClientOption);
        mLocationClient.startLocation();
        mLocationClient.setLocationListener(this);
    }

    @Override
    public void searchLiveWeatherByAdCode(String adCode) {
        WeatherSearchQuery query = new WeatherSearchQuery(adCode, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch search = new WeatherSearch(context);
        search.setOnWeatherSearchListener(this);
        search.setQuery(query);
        search.searchWeatherAsyn();
    }

    @Override
    public void getSemesterWeekInfo() {
        SemesterWeekInfo semesterWeekInfo = new SemesterWeekInfo();
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        semesterWeekInfo.setTime(TimeUtils.millis2String(currentTime, simpleDateFormat));
        semesterWeekInfo.setLunar(TimeUtils.solarToLunar(currentTime));
        semesterWeekInfo.setWeek(TimeUtils.getWeek(TimeUtils.getWeekIndex(currentTime)));
        semesterWeekInfo.setSemesterWeek("12");
        semesterWeekInfoLoadSuccess(semesterWeekInfo);
    }

    @Override
    public void semesterWeekInfoLoadSuccess(SemesterWeekInfo semesterWeekInfo) {
        adapter.updateItemSemesterWeek(semesterWeekInfo);
    }

    @Override
    public void showDialog(int type, int position) {
        HomeItemDialogFragment fragment = HomeItemDialogFragment.newInstance(type, position);
        fragment.setOnItemClickListener(this);
        fragment.show(getChildFragmentManager(), TAG_ITEM);
    }

    @Override
    public void onUpdateClick(String url) {
        Intent intent = new Intent(context, VersionUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initItemViewMySchedule();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }

        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initItemViewMySchedule();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onItemClick(AppCenterItemInfo itemInfo) {
        if (listener != null) {
            listener.onItemClick(itemInfo);
        }
    }

    @Override
    public void onItemClick(ArrayList<AppCenterItemInfo> list) {
        Intent intent = new Intent(context, SubscriptionCenterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        intent.putExtras(bundle);
        openActivityForResult(intent, REQUEST_CODE_SUBSCRIPTION_CENTER);
    }

    @Override
    public void OnBannerClick(int position) {
        if (bannerInfoList == null || bannerInfoList.size() <= position ||
                bannerInfoList.get(position) == null) {
            LoggerHelper.e(TAG, " OnBannerClick " + " bannerInfoList = " + bannerInfoList +
                    " position = " + position);
            return;
        }

        String title = bannerInfoList.get(position).getTitle(); //标题
        String url = bannerInfoList.get(position).getUrl(); //url
        openWebViewActivity(title, url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 订阅中心
         */
        case REQUEST_CODE_SUBSCRIPTION_CENTER:
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    ArrayList<AppCenterItemInfo> list = bundle.getParcelableArrayList("list");
                    headerAdapter.setDataSource(list);
                }
            }
            break;


        default:
            break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation == null) {
            LoggerHelper.e(TAG, " onLocationChanged aMapLocation = " + null);
            return;
        }

        int errorCode = aMapLocation.getErrorCode();
        switch (errorCode) {
        /*
         * 定位成功
         */
        case 0:
            String adCode = aMapLocation.getAdCode();
            searchLiveWeatherByAdCode(adCode);
            break;

        default:
            LoggerHelper.e(TAG, " onLocationChanged " + "errorCode = " + errorCode + " 失败信息 : " +
                    aMapLocation.getErrorInfo());
            searchLiveWeatherByAdCode("杭州市");
            break;
        }
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherLive = weatherLiveResult.getLiveResult();
                adapter.updateItemWeatherToday(weatherLive);
            } else {
                LoggerHelper.e(TAG, "onWeatherLiveSearched 没有搜索到相关的信息");
            }
        } else {
            LoggerHelper.e(TAG, " onWeatherLiveSearched 天气信息获取失败 " + " rCode = " + rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }

    @Override
    public void onMyScheduleItemClick(long id) {
        Intent intent = new Intent(context, ScheduleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public void onItemMoreClick(int type, int position) {
        showDialog(type, position);
    }

    @Override
    public void onItemIgnoreClick(int type, int position) {
        adapter.removeItem(position);
        showToastMsgShort(" onItemIgnoreClick " + type);
    }

    @Override
    public void onItemNoLongerClick(int type, int position) {
        adapter.removeItem(position);
        showToastMsgShort(" onItemNoLongerClick " + type);
    }

    //图片加载器
    private static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path != null && path instanceof String) {
                ImageLoaderHelper.loadImage(context, imageView, (String) path);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调接口
     */
    public interface OnItemClickListener {

        void onItemClick(AppCenterItemInfo itemInfo);
    }


    @Override
    protected boolean immersionEnabled() {
        return true;
    }

    @Override
    protected void immersionInit() {
        super.immersionInit();
        ImmersionStatusBarUtils.initStatusBarInHomeFragment(ll_home_search, immersionBar);
    }

    //内部类
    private class OnScrollRecyclerView extends RecyclerView.OnScrollListener {

        int totalDy;
        int bannerHeight = banner.getLayoutParams().height - ll_home_search.getLayoutParams().height -
                context.getResources().getDimensionPixelSize(R.dimen.common_status_bar_height);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalDy += dy;
            if (totalDy <= bannerHeight) {
                float alpha = (float) totalDy / bannerHeight;
                ll_home_search.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                        , ContextCompat.getColor(context, R.color.colorPrimary), alpha));
            } else {
                ll_home_search.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                        , ContextCompat.getColor(context, R.color.colorPrimary), 1));
            }
        }
    }
}
