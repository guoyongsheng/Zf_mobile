package com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire;

import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 编辑问卷调查的协议接口
 */

interface EditQuestionnaireContract {

    interface View extends BaseView {

        /**
         * 根据id获取问卷调查信息
         *
         * @param id id
         * @return QuestionnaireItemInfo对象
         */
        QuestionnaireItemInfo getItemInfoById(long id);
    }
}
