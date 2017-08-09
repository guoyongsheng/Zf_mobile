package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/20.
 */

public interface AddAddressContract {
    interface  View extends BaseView<AddAddressPresenter>{
        /**
         * 提交新的地址成功
         * @param msg
         */
        void OpreateSuccess(String msg);

        /**
         * 失败
         * @param err
         */
        void OpreateErr(String err);
        /**
         * 修改地址
         */
         void editAddress();
        /**
         * 删除地址
         */
        void deleteAddress();
        /**
         * 提交新地址
         */
        void submitAddress();

    }

   interface  Presenter extends BasePresenter{

       void submitAddress(Map<String,String> params);
       void deleteAddress(Map<String,String> params);
       void edtiAddress(Map<String,String> params);
}

}
