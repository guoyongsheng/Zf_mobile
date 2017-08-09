package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 读者信息协议接口
 */

interface ReaderInformationContract {

    interface View extends BaseView<ReaderInformationPresenter> {

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
