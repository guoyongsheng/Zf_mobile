package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import com.zfsoftmh.entity.QuestionnaireQuestionInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description:
 */

interface JoinQuestionnaireContract {


    interface View extends BaseView<JoinQuestionnairePresenter> {

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
         * 参与成功
         */
        void joinSuccess();

        /**
         * 参与失败
         *
         * @param errorMsg 失败信息
         */
        void joinFailure(String errorMsg);

        /**
         * 参与问卷调查
         */
        void joinQuestionnaire();

        /**
         * 判断是否还有问题没有回答
         *
         * @return true: 是  false: 没有
         */
        boolean checkHasQuestionNoAnswer();

        /**
         * list转string
         *
         * @param list 集合
         * @return 字符串
         */
        String getValue(List<QuestionnaireQuestionInfo> list);
    }


    interface Presenter extends BasePresenter {

        /**
         * 参与问卷调查
         *
         * @param answer 答案
         */
        void joinQuestionnaire(String answer);
    }
}
