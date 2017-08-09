package com.zfsoftmh.ui.modules.chatting.tribe;

import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.tribe.IYWTribeService;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/12.
 */
interface TribeContract {

    interface View extends BaseView<TribePresenter> {
        void onLoading();
        void onStopLoading();
        void refreshAdapter(ArrayList<YWTribe> dataList);
        void showErrMsg(String msg);
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取群组服务
         */
        IYWTribeService getTribeService();

        /**
         * 获取数据
         */
        void getAllTribesFromServer();

        void onDestroy();
    }
}
