package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: 退回待办事宜的协议接口
 */

interface WithDrawMattersContract {

    interface View extends BaseView<WithDrawMattersPresenter> {

        /**
         * 获取退回的内容
         *
         * @return 退回的内容
         */
        String getWithDrawContent();


        /**
         * 退回待办事宜
         */
        void withDrawMatters();
    }


    interface Presenter extends BasePresenter {

    }
}
