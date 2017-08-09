package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine;

import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * Created by ljq
 * on 2017/7/19.
 */

public interface SchoolEateryContract {
    interface  View extends BaseListView<SchoolEateryPresenter,EateryInfo> {
        /**
         * 获取定位信息
         */
      void location();


    }
   interface  Presenter extends BasePresenter{
       void loadData(Map<String,String> params);
   }


}
