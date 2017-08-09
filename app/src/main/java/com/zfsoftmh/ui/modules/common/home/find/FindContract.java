package com.zfsoftmh.ui.modules.common.home.find;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 发现的协议接口
 */

interface FindContract {

    interface View extends BaseView<FindPresenter> {

        /**
         * 初始化headerView
         */
        void initHeaderView();

        /**
         * 初始化item
         */
        void initItemView();

        /**
         * 获取轮播图信息
         */
        void getBannerInfo();
    }


    interface Presenter extends BasePresenter {

    }
}
