package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published;

import com.zfsoftmh.entity.QuestionnairePublishedInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 已发布协议接口
 */

interface PublishedContract {

    interface View extends BaseListView<PublishedPresenter, QuestionnairePublishedInfo> {

        /**
         * 判断用户是否有权限查看问卷调查
         *
         * @param info 问卷调查信息
         * @return true: 有权限  false: 没有权限
         */
        boolean checkHasPermission(QuestionnairePublishedInfo info);

        /**
         * 判断用户是否已經參與過
         *
         * @param info 问卷调查信息
         * @return true: 參與過  false: 沒有參與過
         */
        boolean checkHasJoined(QuestionnairePublishedInfo info);

        /**
         * 判断问卷调查是否已经结束了
         *
         * @param info 问卷调查信息
         * @return true: 结束了  false: 没有结束
         */
        boolean checkHasFinished(QuestionnairePublishedInfo info);

        /**
         * 檢查是否有問題列表
         *
         * @param info 问卷调查信息
         * @return true: 有  false: 沒有
         */
        boolean checkHasQuestion(QuestionnairePublishedInfo info);
    }

    interface Presenter extends BasePresenter {

        /**
         * 分页获取数据
         *
         * @param startPage 起始页
         * @param pageSize  每页加载多少条数据
         */
        void loadData(int startPage, int pageSize);
    }

}
