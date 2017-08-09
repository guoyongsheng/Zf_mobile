package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency;

import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 待办事宜的协议接口
 */

interface AgencyMattersContract {

    interface View extends BaseView<AgencyMattersPresenter> {

        /**
         * 控制是否已經初始化
         *
         * @param isInit true: 初始化  false: 沒有初始化
         */
        void setIsInit(boolean isInit);

        /**
         * 控制是否正在加载
         *
         * @param isLoading 是否正在加载
         */
        void setIsLoading(boolean isLoading);

        /**
         * 设置是否已经加载过了
         *
         * @param isLoaded true: 加载过了   false: 还没有加载
         */
        void setIsLoaded(boolean isLoaded);

        /**
         * 控制是否正在刷新
         *
         * @param isRefreshing true: 正在刷新  false: 沒有在刷新
         */
        void setIsRefreshing(boolean isRefreshing);

        /**
         * 判断是否可以加载数据了
         *
         * @return true: 可以  false: 不可以
         */
        boolean checkIsCanLoadData();

        /**
         * 加载数据
         *
         * @param start 起始页
         * @param flag  0: 滚动加载  1: 下拉刷新
         */
        void loadData(int start, int flag);

        /**
         * 加载成功
         *
         * @param list AgencyMattersInfo集合
         */
        void loadSuccess(AgencyMattersInfo list);

        /**
         * 加载失败
         *
         * @param errorMsg 错误信息
         */
        void loadFailure(String errorMsg);

    }

    interface Presenter extends BasePresenter {

        void loadData(int start, int size, String sign);
    }
}
