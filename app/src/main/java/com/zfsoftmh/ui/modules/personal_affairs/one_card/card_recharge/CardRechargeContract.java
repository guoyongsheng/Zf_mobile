package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

import com.zfsoftmh.pay.entity.BizContent;
import com.zfsoftmh.pay.entity.SignInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 卡片充值的协议接口
 */

interface CardRechargeContract {

    interface View extends BaseView<CardRechargePresenter> {


        /**
         * 显示充值金额对话框
         */
        void showCardRechargeDialog();

        /**
         * 判断输入金额是否为空
         *
         * @param rechargeAmount 充值金额
         * @return true: 为空  false: 不为空
         */
        boolean checkRechargeAmountIsNull(String rechargeAmount);

        /**
         * 获取充值金额
         *
         * @return 充值金额
         */
        String getCardRecharge();

        /**
         * 支付
         */
        void recharge();

        /**
         * 获取业务参数
         *
         * @return 业务参数
         */
        BizContent getBizContent();

        /**
         * 封裝sign对象
         *
         * @param app_id     app_id
         * @param method     方法名
         * @param timestamp  请求时间
         * @param bizContent 业务参数
         * @return string类型的数据
         */
        String getSignInfo(String app_id, String method, String timestamp, BizContent bizContent);

        /**
         * 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
         *
         * @return 时间的字符串
         */
        String getTimeStamp();

        /**
         * 获取签名
         *
         * @param orderParam 订单的参数
         */
        void getSign(String orderParam, String biz_content_android);

        /**
         * 显示对话框
         *
         * @param msg 对话框要显示的信息
         */
        void showDialog(String msg);

        /**
         * 隐藏对话框
         */
        void hideDialog();

        /**
         * 加载失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 加载成功
         *
         * @param signInfo 签名信息
         */
        void loadSuccess(SignInfo signInfo);
    }


    interface Presenter extends BasePresenter {

        /**
         * 获取签名
         *
         * @param orderParam 订单的参数
         */
        void getSign(String orderParam, String biz_content_android);
    }
}
