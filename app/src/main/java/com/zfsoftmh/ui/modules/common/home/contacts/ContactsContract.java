package com.zfsoftmh.ui.modules.common.home.contacts;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 通讯录协议接口
 */
interface ContactsContract {

    interface View extends BaseView<ContactsPresenter> {

        /**
         * 判断用户有没有登录
         *
         * @return true: 登录  false: 没有登录
         */
        boolean checkUserIsLogin();

        /**
         * 请求获取联系人的权限
         */
        void requestPermission();

        /**
         * 创建对话框
         */
        void createAppSettingDialog();

        /**
         * 获取手机联系人
         */
        void getPhoneContacts();
    }

    interface Presenter extends BasePresenter {

    }
}
