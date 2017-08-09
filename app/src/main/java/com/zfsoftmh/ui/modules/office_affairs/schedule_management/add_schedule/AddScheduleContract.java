package com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-5-24
 * @Description: 新建我的日程的协议接口
 */

interface AddScheduleContract {


    interface View extends BaseView {

        /**
         * 初始化新增状态
         */
        void initAddMode();

        /**
         * 初始化编辑状态
         */
        void initEditMode();

        /**
         * 获取当前时间 格式 yyyy-MM-dd hh:mm:ss
         *
         * @return 当前时间
         */
        String getCurrentTime();

        /**
         * 获取当前时间的后一天
         *
         * @return 后一天的时间
         */
        String getAfterDay();

        /**
         * 格式转换 yyyy-MM-dd hh:mm:ss
         *
         * @param selectedDate 选中的日期
         * @param hourOfDay    小时
         * @param minute       分钟
         * @return 时间字符串
         */
        String parseDate(SelectedDate selectedDate, int hourOfDay, int minute);

        /**
         * 字符串转换为long
         *
         * @param value 时间格式的字符串
         * @return long
         */
        long stringToLong(String value);

        /**
         * 显示结束时间对话框
         */
        void showStartTimeDialog();

        /**
         * 显示开始时间对话框
         */
        void showEndTimeDialog();

        /**
         * 显示提醒时间的对话框
         */
        void showHintTimeDialog();

        /**
         * 显示重复次数对话框
         */
        void showRepeatTimesDialog();

        /**
         * 完成
         */
        void onDoneClick();

        /**
         * 获取日程标题
         *
         * @return 标题
         */
        String getScheduleTitle();


        /**
         * 判断开始时间是否小于当前时间
         *
         * @param startTime   开始时间
         * @param currentTime 当前时间
         * @return true: 小于 false: 大于
         */
        boolean checkStartTimeIsLessCurrentTime(String startTime, long currentTime);


        /**
         * 判断结束时间是否小于开始时间
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return true: 小于 false: 大于
         */
        boolean checkEndTimeIsLessStartTime(String startTime, String endTime);

        /**
         * 获取开始时间
         *
         * @return 开始时间
         */
        String getStartTime();

        /**
         * 获取结束时间
         *
         * @return 结束时间
         */
        String getEndTime();

        /**
         * 判断标题是否为空
         *
         * @param title 标题
         * @return true: 空 false: 不为空
         */
        boolean checkTitleIsNull(String title);

        /**
         * 根据id获取数据库中的数据
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
         * 插入数据
         *
         * @param info ScheduleManagementInfo对象
         */
        void insert(ScheduleManagementInfo info);

        /**
         * 跟新数据
         *
         * @param info info
         */
        void update(ScheduleManagementInfo info);

        /**
         * 获取提醒类型
         *
         * @return 提醒类型
         */
        int getHintType();

        /**
         * 获取重复类型
         *
         * @return 重复类型
         */
        int getRepeatType();

        /**
         * yyyy-MM-dd HH:mm:ss 时间转换为 yyyy年MM月
         *
         * @param startTime 开始时间
         * @param type      类型 0：转换为 yyyy年MM月  1：转换为 yyyy-Mm-dd
         * @return yyyy年MM月
         */
        String startTimeParseTime(String startTime, int type);

        /**
         * yyyy-MM-dd HH:mm:ss 时间转换为年份
         *
         * @param value yyy-MM-dd HH:mm:ss格式的字符串
         * @return 年份
         */
        int getYear(String value);

        /**
         * yyyy-MM-dd HH:mm:ss 时间转换为月份
         *
         * @param value yyy-MM-dd HH:mm:ss格式的字符串
         * @return 月份
         */
        int getMonth(String value);

        /**
         * yyyy-MM-dd HH:mm:ss 时间转换为天
         *
         * @param value yyy-MM-dd HH:mm:ss格式的字符串
         * @return 天
         */
        int getDay(String value);

        /**
         * 添加通知
         *
         * @param info 日程信息
         */
        void addNotification(ScheduleManagementInfo info);
    }
}

