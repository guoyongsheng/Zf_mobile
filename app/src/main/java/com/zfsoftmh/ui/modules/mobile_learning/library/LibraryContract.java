package com.zfsoftmh.ui.modules.mobile_learning.library;

import com.zfsoftmh.entity.LibraryInfo;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 图书馆协议接口
 */

interface LibraryContract {

    interface View extends BaseView {

        /*
         * 获取数据源
         * @return 图书馆集合
         */
        List<LibraryInfo> getDataSource();
    }
}
