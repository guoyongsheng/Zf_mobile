package com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 借阅信息的协议接口
 */

interface BorrowInformationContract {

    interface View extends BaseView<BorrowInformationPresenter> {

        /**
         * 加载数据
         */
        void loadData();

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         */
        void loadData();
    }
}
