package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description:
 */

interface EditNoteLabelContract {
    interface View extends BaseView<EditNoteLabelPresenter> {
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
         * 获取标签数据
         */
        void loadData();

        /**
         * 添加数据
         *
         * @param list
         */
        void setDataList(ArrayList<NoteLabelItemInfo> list);

        /**
         * 提交修改数据
         *
         * @param memoCatalogNameList
         * @param memoCatalogColorList
         */
        void submitData(String memoCatalogNameList, String memoCatalogColorList);

        /**
         * 点击完成按钮跳转
         */
        void skipLabelPage();
    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         */
        void loadData();

        /**
         * 提交数据
         */

        void submitData(String memoCatalogNameList, String memoCatalogColorList);

    }
}
