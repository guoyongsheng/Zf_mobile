package com.zfsoftmh.ui.modules.office_affairs.questionnaire.hit_the_title;

import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 打分题协议接口
 */

interface HitTheTitleContract {

    interface View extends BaseView {

        /**
         * 获取标题
         *
         * @return 标题
         */
        String getTitle();

        /**
         * 判读标题是否为空
         *
         * @param title 标题
         * @return true: 空 false: 不为空
         */
        boolean checkTitleIsNull(String title);

        /**
         * 创建
         */
        void create();
    }
}
