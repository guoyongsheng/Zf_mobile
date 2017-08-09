package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description:
 */

interface IntegralGoodsDetailContract {

    interface View extends BaseView<IntegralGoodsDetailPresenter> {
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
         * 兑换商品
         */
        void exchangeGoods();

        /**
         * 加载失败提示信息
         *
         * @param s
         */
        void loadFailure(String s);

        /**
         * 成功兑换商品
         *
         * @param data 返回总积分
         */
        void exchangeGoodsSuccess(String data);

        /**
         * 跳转
         */
        void skipPage();

    }

    interface Presenter extends BasePresenter {

        /**
         * 兑换商品
         *
         * @param params 请求参数
         */
        void giftExchange(Map<String, String> params);
    }

}
