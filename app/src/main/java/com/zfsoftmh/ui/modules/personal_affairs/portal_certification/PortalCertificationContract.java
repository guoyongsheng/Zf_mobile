package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: 门户认证的协议接口
 */

interface PortalCertificationContract {


    interface View extends BaseView<PortalCertificationPresenter> {

    }


    interface Presenter extends BasePresenter {

        /**
         * 门户认证
         *
         * @param url 网址
         */
        void portal_certification(String url);
    }
}
