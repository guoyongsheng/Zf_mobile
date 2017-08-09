package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import com.zfsoftmh.entity.CloudNoteInfo;
import com.zfsoftmh.entity.CloudNoteListInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description:
 */

interface CloudNoteListContract {
    interface View extends BaseListView<CloudNoteListPresenter,CloudNoteListInfo> {
        /**
         * 从服务器获取便签数据
         */
        void getLabelData();


        /**
         * 数据加载框
         *
         * @param msg
         */
        void createLoadingDialog(String msg);

        /**
         * 隐藏加载框
         */
        void hideUpLoadingDialog();

        /**
         * 获取标签数据列表
         *
         * @param data
         */
        void setLabelData(List<NoteLabelItemInfo> data);

        /**
         * 获取笔记列表数据
         *
         * @param listData
         */
        void setNoteListData(CloudNoteInfo listData);

        /**
         * 加载失败
         *
         * @param errMsg
         */
        void upLoadFailure(String errMsg);


        /**
         * 判断是否是编辑状态
         *
         * @param title 标题
         * @return true: 编辑状态 false：不是编辑状态
         */
        boolean checkIsEditMode(String title);

        /**
         * 加载云笔记列表数据
         *
         * @param startPage          起始页
         * @param isLoadOrRefreshing 0：下拉刷新  1: 滚动加载
         */
        void loadCloudNoteData(int startPage, int isLoadOrRefreshing);

        /**
         * 删除云笔记
         */
        void deleteNote();

        /**
         * 笔记删除成功
         */
        void deleteNoteSuccess();

        /**
         * 云笔记列表正在编辑状态
         */
        void editMode();

        /**
         * 云笔记编辑完成状态
         */
        void noEditMode();



    }

    interface Presenter extends BasePresenter {

        /**
         * 加载标签数据
         */
        void loadLabel();

        /**
         * 加载笔记列表数据
         *
         * @param params
         */
        void loadCloudNoteList(Map<String, String> params);

        /**
         * 删除云笔记
         *
         * @param noteName
         */
        void deleteNote(String noteName);

    }
}
