package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import com.zfsoftmh.entity.DigitalFileItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 主子档案详情协议接口
 */

interface DigitalFileDetailContract {

    interface View extends BaseListView<DigitalFileDetailPresenter, DigitalFileItemInfo> {

        /**
         * 注入实例
         */
        void inject();
    }


    interface Presenter extends BasePresenter {

        /**
         * 根据部门id分页获取部门列表信息
         *
         * @param id    部门id
         * @param start 起始页
         * @param size  每页获取多少条数据
         */
        void getDigitalFileItemInfo(String id, int start, int size);
    }
}
