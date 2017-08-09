package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 充值明细协议接口
 */

interface RechargeDetailsContract {


    interface View extends BaseListView<RechargeDetailsPresenter, OneCardItemDetailsInfo> {

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         *
         * @param startPage 起始页
         * @param pageSize  每页加载多少条数据
         * @param oneCardId 一卡通的id
         */
        void loadData(int startPage, int pageSize, String oneCardId);
    }
}
