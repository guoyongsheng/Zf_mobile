package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done;

import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 已办事宜协议接口
 */

interface HasBeenDoneMattersContract {

    interface View extends BaseView<HasBeenDoneMattersPresenter> {

        /**
         * 加载数据
         *
         * @param start 起始页
         * @param flag  0: 滚动加载  1: 下拉刷新
         */
        void loadData(int start, int flag);

        /**
         * 是否可以加载数据
         *
         * @return true: 可以加载数据  false: 不可以
         */
        boolean isCanLoadData();

        /**
         * 控制是否初始化
         *
         * @param isInit 是否初始化
         */
        void setIsInit(boolean isInit);

        /**
         * 控制是否正在加载
         *
         * @param isLoading 是否正在加载
         */
        void setIsLoading(boolean isLoading);

        /**
         * 是否已经加载过了
         *
         * @param isLoaded true: 已经加载过了  false: 可能还没有加载完
         */
        void setIsLoaded(boolean isLoaded);

        /**
         * 加载成功
         *
         * @param list 成功后返回的数据
         */
        void loadSuccess(List<AgencyMattersInfo> list);

        /**
         * 加载失败
         *
         * @param errorMsg 失敗的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 设置是否正在刷新
         *
         * @param isRefreshing 是否正在刷新
         */
        void setIsRefreshing(boolean isRefreshing);

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         *
         * @param start 起始页
         * @param size  每页加载多少条数据
         */
        void loadData(int start, int size);
    }
}
