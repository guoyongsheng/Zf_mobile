package com.zfsoftmh.ui.modules.common.splash;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 契约接口
 */
interface SplashContract {

    interface View extends BaseView<SplashPresenter> {

        /**
         * 统计安装人数
         */
        void addUpInstallNumber();

        /**
         * 开始动画
         */
        void beginTransition();
    }

    interface Presenter extends BasePresenter {

        /**
         * 统计安装人数
         */
        void addUpInstallNumber();

    }
}
