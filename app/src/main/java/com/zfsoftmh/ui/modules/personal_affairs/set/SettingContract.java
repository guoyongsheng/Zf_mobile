package com.zfsoftmh.ui.modules.personal_affairs.set;

import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.Map;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 设置界面的协议接口
 */

interface SettingContract {

    interface View extends BaseView<SettingPresenter> {

        /**
         * 检测版本跟新
         */
        void checkVersion();

        /*
         * 创建对话框
         */
        void createAppSettingDialog();


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
         * 获取缓存文件的大小
         *
         * @return 缓存文件大小
         */
        String getCacheSize();

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
    }
}
