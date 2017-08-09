package com.zfsoftmh.ui.modules.common.home.school_circle.my_attention;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 我的关注协议接口
 */

interface MyAttentionContract {

    interface View extends BaseView<MyAttentionPresenter> {

        /**
         * 注入实例
         */
        void inject();
    }


    interface Presenter extends BasePresenter {

    }
}
