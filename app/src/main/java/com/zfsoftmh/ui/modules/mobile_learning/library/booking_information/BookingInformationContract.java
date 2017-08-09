package com.zfsoftmh.ui.modules.mobile_learning.library.booking_information;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 预约信息协议接口
 */

interface BookingInformationContract {


    interface View extends BaseView<BookingInformationPresenter> {

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
