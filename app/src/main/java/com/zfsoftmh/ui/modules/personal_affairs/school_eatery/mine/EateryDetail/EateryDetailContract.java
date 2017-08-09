package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import com.zfsoftmh.entity.FoodCataListInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * Created by ljq
 * on 2017/7/25.
 */

 interface EateryDetailContract {

    interface  View extends BaseView<EateryDetailPresenter>{
           void getFoodSucess(List<FoodCataListInfo> list);
           void getFoodErr(String err);
    }


    interface  Presenter extends BasePresenter{
      void loadData(String eateryId);

    }



}
