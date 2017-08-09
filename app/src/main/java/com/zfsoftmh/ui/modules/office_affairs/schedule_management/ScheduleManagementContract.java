package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: 日程管理的协议接口
 */

interface ScheduleManagementContract {


    interface View extends BaseView<ScheduleManagementPresenter> {

        /**
         * toolbar的标题
         *
         * @param title 标题
         */
        void onTitleChanged(String title);

        /**
         * 设置当前的item
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void setCurrentItem(int year, int month, int day);

        /**
         * 刷新数据
         *
         * @param list 数据源
         */
        void refreshData(List<ScheduleManagementInfo> list);

        /**
         * 根据年份和月份获取数据源
         *
         * @param year  年
         * @param month 月
         * @param day   日
         * @return 数据源
         */
        List<ScheduleManagementInfo> getDataSource(int year, int month, int day);

        /**
         * 刷新界面
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void refreshUI(int year, int month, int day);

        /**
         * 转化为yyyy-MM-dd
         *
         * @param year  年
         * @param month 月
         * @param day   日
         * @return yyyy-MM-dd
         */
        String parseTime(int year, int month, int day);

    }

    interface Presenter extends BasePresenter {

    }
}
