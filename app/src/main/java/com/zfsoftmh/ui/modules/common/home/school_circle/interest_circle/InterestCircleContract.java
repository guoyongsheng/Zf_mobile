package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import com.zfsoftmh.entity.InterestCircleItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: 兴趣圈的协议接口
 */

interface InterestCircleContract {

    interface View extends BaseListView<InterestCirclePresenter, InterestCircleItemInfo> {


        /**
         * 初始化搜索的header
         */
        void initHeaderViewSearch(android.view.View view);

        /**
         * 初始化标题的headerView
         */
        void initHeaderViewTitle(android.view.View view);

        /**
         * 初始化viewPager的headerView
         */
        void initHeaderViewItem(android.view.View view);

        /**
         * 初始化标题的headerView
         */
        void initHeaderViewTitleInterest(android.view.View view);

        /**
         * 获取头部的item
         */
        void getHeaderItems();

        /**
         * 头部的item获取成功
         *
         * @param list 列表
         */
        void headerItemsLoadSuccess(ArrayList<InterestCircleItemInfo> list);

        /**
         * 头部的item获取失败
         *
         * @param errorMsg 失败的信息
         */
        void headerItemsLoadFailure(String errorMsg);

        /**
         * 初始化我的圈子的更多
         *
         * @param list 我的圈子的集合
         */
        void initMyCircleMore(ArrayList<InterestCircleItemInfo> list);


        /**
         * 注入
         */
        void inject();
    }


    interface Presenter extends BasePresenter {

        /**
         * 获取我的圈子
         *
         * @param start 开始页
         * @param size  每页请求多少条数据
         */
        void getMyCircle(int start, int size);
    }
}
