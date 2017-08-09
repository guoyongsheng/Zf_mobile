package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * Created by li on 2017/7/31.
 */

public interface OrderSubmitContract {

    interface  View extends BaseView<OrderSubmitPresenter>{
     void submitorder();
        void SubmitOrderSucess(String err);
        void SubmitOrderErr(String err);
    }

    interface Presenter extends BasePresenter{
      void SubmitOrder(String data);

    }

}
