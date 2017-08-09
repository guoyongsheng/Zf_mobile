package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import com.zfsoftmh.entity.IntegralIncomeItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

interface IntegralIncomeContract {
    interface View extends BaseListView<IntegralIncomePresenter, IntegralIncomeItemInfo> {
        /**
         * 数据加载框
         *
         * @param msg
         */
        void createLoadingDialog(String msg);

        /**
         * 隐藏加载框
         */
        void hideUpLoadingDialog();

        /**
         * 获取积分收入明细
         *
         * @param start_page 起始页
         * @param PAGE_SIZE  每页数据条数
         */
        void getIntegralIncomeList(int start_page, int PAGE_SIZE);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取积分收支明细
         *
         * @param params
         */
        void getIntegralIncome(Map<String, String> params);
    }
}
