package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record.ExchangeRecordPresenter;

/**
 * @author wangshimei
 * @date: 17/7/14
 * @Description: 兑换记录详情
 */

public interface ExchangeRecordDetailContract {


    interface View extends BaseView<ExchangeRecordDetailPresenter> {
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
         * 加载失败提示信息
         *
         * @param s
         */
        void loadFailure(String s);

        /**
         * 加载内容信息
         */
        void loadDetailInfo();

        /**
         * 成功加载内容信息
         *
         * @param info
         */
        void loadDetailInfoSuccess(IntegralMallGoodsInfo info);

        /**
         * 设置页面值
         */
        void setData();
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取兑换记录商品详情
         *
         * @param goodsid 商品ID
         */
        void getRecordDetail(String goodsid);
    }
}
