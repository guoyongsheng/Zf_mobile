package com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 校园动态协议接口
 */

interface SchoolDynamicsContract {


    interface View extends BaseView<SchoolDynamicsPresenter> {

        /**
         * 注入
         */
        void inject();
    }


    interface Presenter extends BasePresenter {

    }
}
