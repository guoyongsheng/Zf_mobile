package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_list;

import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 接口
 */

interface ScheduleListContract {


    interface View extends BaseView {

        /**
         * 获取所有的数据
         *
         * @return ScheduleManagementInfo集合
         */
        List<ScheduleManagementInfo> getAllData();

        /**
         * 刷新界面
         *
         * @param list ScheduleManagementInfo集合
         */
        void refreshUI(List<ScheduleManagementInfo> list);
    }
}
