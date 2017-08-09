package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform;

import com.zfsoftmh.entity.OrderForminfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * Created by li
 * on 2017/7/19.
 */

public interface OrderFormContract {
    interface View extends BaseListView<OrderFormPresenter,OrderForminfo> {

    }


    interface Presenter extends BasePresenter{
        void loadData(String start,String size);
    }

}
