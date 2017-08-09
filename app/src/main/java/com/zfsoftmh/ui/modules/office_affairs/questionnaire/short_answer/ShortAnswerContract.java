package com.zfsoftmh.ui.modules.office_affairs.questionnaire.short_answer;

import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 简答题的协议接口
 */

interface ShortAnswerContract {

    interface View extends BaseView {

        /**
         * 获取标题
         *
         * @return 标题
         */
        String getTitle();

        /**
         * 判断标题是否为空
         *
         * @param title 标题
         * @return true：空 false: 不为空
         */
        boolean checkTitleIsNull(String title);

        /**
         * 创建
         */
        void create();

        /**
         * 根据id获取问卷详情信息
         *
         * @param id id
         * @return QuestionnaireDetailInfo详情
         */
        QuestionnaireDetailInfo getInfoById(long id);
    }
}
