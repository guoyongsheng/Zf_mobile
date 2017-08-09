package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement;

import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description:
 */

interface SettlementMattersContract {

    interface View extends BaseView<SettlementMattersPresenter> {

        /**
         * 加载数据
         *
         * @param start 起始页
         * @param flag  0: 滚动加载  1: 下拉刷新
         */
        void loadData(int start, int flag);

        /**
         * 判断是否可以加载数据
         *
         * @return true: 可以加载  false: 不可以
         */
        boolean isCanLoadData();

        /**
         * 控制是否已经初始化
         *
         * @param isInit 是否已经初始化
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
         * @param isLoaded true: 已經加载过了  false: 没有加载过
         */
        void setIsLoaded(boolean isLoaded);

        /**
         * 是否正在刷新
         *
         * @param isRefreshing true: 正在刷新  false: 沒有在刷新
         */
        void setIsRefreshing(boolean isRefreshing);

        /**
         * 数据加载成功
         *
         * @param list 办结事宜集合
         */
        void loadSuccess(List<AgencyMattersInfo> list);

        /**
         * 加载失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);
    }

    interface Presenter extends BasePresenter {

        /**
         * 加载办结事宜
         *
         * @param start 起始页
         * @param size  每页加载多少条数据
         */
        void loadData(int start, int size);

    }
}
