package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail;

import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 接口
 */

interface ScheduleDetailContract {


    interface View extends BaseView {

        /**
         * 根据id获取数据
         *
         * @param id id
         * @return ScheduleManagementInfo对象
         */
        ScheduleManagementInfo getScheduleManagementInfo(long id);

        /**
         * 根据类型获取提醒时间
         *
         * @param type 类型
         * @return 提醒时间
         */
        String getHintTimeByHintType(int type);

        /**
         * 根据类型获取重复次数
         *
         * @param type 类型
         * @return 重复次数
         */
        String getRepeatTimeByRepeatType(int type);

        /**
         * 根据id删除数据库中的对象
         *
         * @param id id
         */
        void deleteDataFromRealm(long id);

        /**
         * 刷新界面
         *
         * @param id id
         */
        void refreshUI(long id);

        /**
         * 获取开始时间
         *
         * @return 开始时间 格式 yyyy-MM-dd HH:mm:ss
         */
        String getStartTime();

        /**
         * 获取年份
         *
         * @param value 时间 格式 yyyy-MM-dd HH:mm:ss
         * @return 年份
         */
        int getYear(String value);


        /**
         * 获取年份
         *
         * @param value 时间 格式 yyyy-MM-dd HH:mm:ss
         * @return 月份
         */
        int getMonth(String value);


        /**
         * 获取年份
         *
         * @param value 时间 格式 yyyy-MM-dd HH:mm:ss
         * @return 天
         */
        int getDay(String value);

        /**
         * 返回
         */
        void onBackPressed();

        /**
         * 判断是否提醒
         *
         * @param hintType   提醒类型
         * @param repeatType 重复类型
         * @return true: 提醒 false: 不提醒
         */
        boolean checkIsHint(int hintType, int repeatType);
    }
}
