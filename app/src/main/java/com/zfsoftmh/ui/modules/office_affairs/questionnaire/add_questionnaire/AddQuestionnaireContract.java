package com.zfsoftmh.ui.modules.office_affairs.questionnaire.add_questionnaire;

import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 创建问卷调查的协议接口
 */

interface AddQuestionnaireContract {

    interface View extends BaseView {

        /**
         * 获取标题
         *
         * @return 标题
         */
        String getTitle();

        /**
         * 获取描述
         *
         * @return 描述
         */
        String getDescription();


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
         * 当前时间的毫秒数转化为 yyyy-MM-dd HH:mm
         *
         * @param currentTime 当前时间
         * @return yyyy-MM-dd HH:mm
         */
        String parseTime(long currentTime);

    }
}
