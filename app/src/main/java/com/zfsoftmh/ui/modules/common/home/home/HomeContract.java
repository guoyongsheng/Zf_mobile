package com.zfsoftmh.ui.modules.common.home.home;

import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.BannerInfo;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.entity.SemesterWeekInfo;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 协议接口
 */
interface HomeContract {

    interface View extends BaseView<HomePresenter> {

        /**
         * 创建对话框
         */
        void createAppSettingDialog();

        /**
         * 版本校验
         */
        void checkVersion();

        /**
         * 显示错误的信息
         *
         * @param errorMsg 错误的信息
         */
        void showErrorMsg(String errorMsg);

        /**
         * 显示数据
         *
         * @param data 加载的数据
         */
        void showData(VersionInfo data);

        /**
         * 显示版本跟新的对话框
         *
         * @param versionInfo 版本信息
         * @param updateType  跟新类型 1：强制跟新 2: 不用强制跟新
         */
        void showVersionDialog(VersionInfo versionInfo, int updateType);

        /**
         * 设置是否是新版本
         *
         * @param isNewVersion true: 新版版   false: 不是新版本
         */
        void setNewVersion(boolean isNewVersion);

        /**
         * 请求版本跟新的权限
         */
        void requestPermissions();

        /**
         * 判读用户是否登录
         *
         * @return true: 登录 false: 没有登录
         */
        boolean checkUserIsLogin();

        /**
         * 获取首页轮播图
         */
        void getHomeBannerInfo();

        /**
         * 轮播图信息获取成功
         *
         * @param list 轮播图的集合
         */
        void loadBannerInfoSuccess(List<BannerInfo> list);

        /**
         * 轮播图的信息转化为 标题集合
         *
         * @param list 轮播图集合
         * @return 标题集合
         */
        List<String> getTitleList(List<BannerInfo> list);

        /**
         * 轮播图的信息转化为 标题集合
         *
         * @param list 轮播图集合
         * @return 图片集合
         */
        List<String> getImageList(List<BannerInfo> list);

        /**
         * 加载失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 获取未登录时的首页服务
         */
        void getHomeServiceWithNoLogin();

        /**
         * 获取登录时的首页服务
         */
        void getHomeServiceWithLogin();

        /**
         * 首页服务获取成功
         *
         * @param list 首页服务集合
         */
        void loadHomeServiceInfoSuccess(ArrayList<AppCenterItemInfo> list);


        /**
         * 获取RecyclerView的item高度
         *
         * @return item高度
         */
        int getItemHeight();

        /**
         * 跳转到WebViewActivity
         *
         * @param title 标题
         * @param url   网址
         */
        void openWebViewActivity(String title, String url);

        /**
         * 获取日程管理列表--- 开始时间大于当前时间的
         *
         * @param time 当前时间
         * @return 日程管理的集合
         */
        List<ScheduleManagementInfo> getScheduleManagementInfoList(long time);

        /**
         * 开始时间转化为时间戳
         *
         * @param startTime 开始时间 格式: yyyy-MM-dd HH:mm:ss
         * @return 时间戳
         */
        long stringParseToLong(String startTime);

        /**
         * 系统当前时间转化为yyyy-MM-dd
         *
         * @param currentTime 当前时间
         * @return yyyy-MM-dd
         */
        String longParseToString(long currentTime);

        /**
         * 请求定位相关的权限
         */
        void requestPermissionsForLocation();

        /**
         * 开始定位
         */
        void startLocation();

        /**
         * 根据adCode查询实时天气
         *
         * @param adCode 城市名称或者adCode
         */
        void searchLiveWeatherByAdCode(String adCode);

        /**
         * 获取学期周的信息
         */
        void getSemesterWeekInfo();

        /**
         * 学期周的数据获取成功
         *
         * @param semesterWeekInfo SemesterWeekInfo对象
         */
        void semesterWeekInfoLoadSuccess(SemesterWeekInfo semesterWeekInfo);

        /**
         * 显示对话框
         *
         * @param type     类型 0：日历的 1：今日天气 2：我的日程
         * @param position 在列表中的位置
         */
        void showDialog(int type, int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * 版本校验
         *
         * @param map 请求的参数
         */
        void checkVersion(Map<String, String> map);

        /**
         * 判断是否需要跟新
         *
         * @param versionInfo 版本信息对象
         * @return true: 需要跟新  false: 不需要跟新
         */
        boolean checkShouldUpdate(VersionInfo versionInfo);

        /**
         * 判断是否需要强制跟新
         *
         * @param versionInfo 版本信息对象
         * @return true: 需要强制跟新  false: 不需要强制跟新
         */
        boolean checkShouldUpdateForced(VersionInfo versionInfo);

        /**
         * 获取首页轮播图
         *
         * @param maxSize 最大几张图
         */
        void getHomeBanner(int maxSize);

        /**
         * 获取未登录时的首页服务
         */
        void getHomeServiceWithNoLogin();

        /**
         * 获取登录时的首页服务
         */
        void getHomeServiceWithLogin();
    }
}
