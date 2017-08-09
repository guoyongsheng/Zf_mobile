package com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details;

import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 消费明细协议接口
 */

interface ConsumerDetailsContract {

    interface View extends BaseView<ConsumerDetailsPresenter> {

        /**
         * 加载数
         *
         * @param startPage          起始页
         * @param isLoadOrRefreshing 0：下拉刷新  1: 滚动加载
         */
        void loadData(int startPage, int isLoadOrRefreshing);

        /**
         * 加载成功
         *
         * @param info 一卡通详情列表
         */
        void loadSuccess(ResponseListInfo<OneCardItemDetailsInfo> info);

        /**
         * 加载失败
         *
         * @param errorMsg 失敗的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 开始刷新
         */
        void startRefreshing();

        /**
         * 停止刷新
         */
        void stopRefreshing();


        /**
         * 转化数据 --- 刷新
         *
         * @param list 一卡通集合
         */
        void mapDataInRefreshing(List<OneCardItemDetailsInfo> list);


        /**
         * 转化数据--- 滚动加载
         *
         * @param list 一卡通集合
         */
        void mapDataInLoading(List<OneCardItemDetailsInfo> list);
    }


    interface Presenter extends BasePresenter {


        /**
         * 分页获取消费明细的数据
         *
         * @param startPage 起始页
         * @param pageSize  每页加载多少条数据
         * @param oneCardId 一卡通id
         */
        void loadData(int startPage, int pageSize, String oneCardId);
    }
}
