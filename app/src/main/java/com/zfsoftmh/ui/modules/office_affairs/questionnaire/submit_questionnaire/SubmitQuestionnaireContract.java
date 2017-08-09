package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 提交问卷调查的协议接口
 */

interface SubmitQuestionnaireContract {

    interface View extends BaseView<SubmitQuestionnairePresenter> {

        /**
         * 获取备注
         *
         * @return 备注内容
         */
        String getNote();

        /**
         * 仅自己可看是否选中
         *
         * @return true: 选中 false: 没选中
         */
        boolean isChecked();

        /**
         * 提交
         */
        void submit();

        /**
         * 根据id获取问卷调查的信息
         *
         * @param id id
         * @return QuestionnaireItemInfo对象
         */
        QuestionnaireItemInfo getQuestionnaireItemInfoById(long id);

        /**
         * 提交成功
         */
        void submitSuccess();

        /**
         * 提交失败
         *
         * @param errorMsg 失败信息
         */
        void submitFailure(String errorMsg);

        /**
         * 显示对话框
         *
         * @param msg 要显示的信息
         */
        void showDialog(String msg);

        /**
         * 隐藏对话框
         */
        void hideDialog();

        /**
         * 跳转到QuestionnaireActivity界面
         */
        void openActivity();
    }

    interface Presenter extends BasePresenter {

        /**
         * 提交问卷调查
         *
         * @param value 问卷调查的信息
         */
        void submitQuestionnaire(String value);
    }
}
