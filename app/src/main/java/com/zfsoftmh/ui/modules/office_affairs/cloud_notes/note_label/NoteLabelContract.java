package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/5/11 0011
 * 编写人：王世美
 * 功能描述：
 */

interface NoteLabelContract {
    interface View extends BaseView<NoteLabelPresenter> {

        /**
         * 显示加载框
         *
         * @param msg
         */
        void showLoadingDialog(String msg);

        /**
         * 隐藏加载框
         */
        void hideLoadingDialog();

        /**
         * 加载失败提示框
         *
         * @param errMsg
         */
        void loadFailure(String errMsg);

        /**
         * 加载标签列表数据成功
         *
         * @param data
         */
        void loadData(List<NoteLabelItemInfo> data);

        /**
         * 加载标签列表数据
         */
        void loadData();

        /**
         * 编辑按钮跳转
         */
        void skipEditLabelPage();

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         */
        void loadData();

    }
}
