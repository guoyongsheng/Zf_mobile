package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description:
 */

public interface IntegralGoodsSearchContract {

    interface View extends BaseListView<IntegralGoodsSearchPresenter, IntegralMallGoodsInfo> {
        /**
         * 加载积分商品列表
         *
         * @param start_page
         * @param PAGE_SIZE
         */
        void loadIntegralGoodsList(int start_page, int PAGE_SIZE);

        /**
         * 积分升降排序
         */
        void integralLiftingState();
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取
         *
         * @param params
         */
        void getIntegralGoodsList(Map<String, String> params);
    }
}
