package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import com.zfsoftmh.entity.MeetingManagementInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 会议管理的协议接口
 */

interface MeetingManagementContract {

    interface View extends BaseListView<MeetingManagementPresenter, MeetingManagementInfo> {

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         *
         * @param start 起始页
         * @param size  每页加载多少条数据
         * @param type  类型 1:我的会议 2：全体会议
         */
        void loadData(int start, int size, int type);
    }
}
