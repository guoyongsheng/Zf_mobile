package com.zfsoftmh.ui.modules.personal_affairs.contacts.contacts_detail;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 通讯录详情的协议接口
 */

interface ContactsDetailContract {

    interface View extends BaseView<ContactsDetailPresenter> {

        /**
         * 请求打电话权限
         */
        void requestCallPermission();

        /**
         * 请求写入联系人的权限
         */
        void requestWriteContactsPermission();

        /**
         * 创建对话框
         */
        void createAppSettingDialog();

        /**
         * 打电话
         */
        void callPhone();

        /**
         * 添加联系人
         */
        void addContracts();

    }

    interface Presenter extends BasePresenter {

    }
}
