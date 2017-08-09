package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import com.zfsoftmh.entity.DigitalFileDepartInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-15
 * @Description: 数字档案协议接口
 */

interface DigitalFileContract {

    interface View extends BaseView<DigitalFilePresenter> {

        /**
         * 获取数字档案的部门信息
         */
        void getDigitalFileDepartInfo();

        /**
         * 部门信息获取成功
         *
         * @param data 部门信息集合
         */
        void loadSuccess(List<DigitalFileDepartInfo> data);

        /**
         * 数据获取失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);
    }


    interface Presenter extends BasePresenter {

        /**
         * 获取数字档案的部门信息
         */
        void getDigitalFileDepartInfo();
    }
}
