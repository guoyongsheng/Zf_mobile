package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager;

import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * Created by ljq on 2017/7/19.
 */

public interface AddressManagerContract {
    interface View extends BaseListView<AddressManagerPresenter,UserAddressInfo> {
         void loadData(ResponseListInfo<UserAddressInfo> data);
    }


    interface Presenter extends BasePresenter{
        void loadData(Map<String, String> params);
    }
}
