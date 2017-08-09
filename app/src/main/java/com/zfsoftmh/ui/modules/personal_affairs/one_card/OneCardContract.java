package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import com.zfsoftmh.entity.OneCardInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 一卡通的协议接口
 */

interface OneCardContract {


    interface View extends BaseView<OneCardPresenter> {

        /**
         * 获取账户余额
         */
        void getAccountBalance();


        /**
         * 开始加载
         */
        void startLoading();

        /**
         * 停止加载
         */
        void stopLoading();

        /**
         * 账号余额获取成功
         *
         * @param info 一卡通对象
         */
        void loadSuccess(OneCardInfo info);


        /**
         * 账户余额获取失败
         *
         * @param errorMsg 失败信息
         */
        void loadFailure(String errorMsg);
    }


    interface Presenter extends BasePresenter {

        /**
         * 获取账户余额
         */
        void getAccountBalance();
    }
}
