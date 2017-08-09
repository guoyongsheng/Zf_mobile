package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import com.zfsoftmh.entity.IntegralRankingInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description:
 */

public interface IntegralRankingContract {

    interface View extends BaseView<IntegralRankingPresenter> {
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
         * 加载积分排名
         */
        void loadIntegralRanking();

        /**
         * 加载失败提示信息
         *
         * @param s
         */
        void loadFailure(String s);

        /**
         * 积分排名加载成功回调方法
         *
         * @param info
         */
        void loadSuccess(IntegralRankingInfo info);

        /**
         * 初始化头布局
         */
        void initHeaderView();

        /**
         * 分解数据
         */
        void divideList();

        /**
         * 加载个人排名
         */
        void serPersonView();

        /**
         * 加载排名列表视图
         */
        void integralRankingView();
    }


    interface Presenter extends BasePresenter {
        /**
         * 获取积分排名数据
         */
        void getIntegralRankingList();
    }
}
