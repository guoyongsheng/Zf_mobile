package com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire;

import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 预览问卷调查的协议接口
 */

interface PreviewQuestionnaireContract {

    interface View extends BaseView {

        /**
         * 根据id获取问卷调查的信息
         * @param id id
         * @return QuestionnaireItemInfo
         */
        QuestionnaireItemInfo getQuestionnaireInfoById(long id);
    }
}
