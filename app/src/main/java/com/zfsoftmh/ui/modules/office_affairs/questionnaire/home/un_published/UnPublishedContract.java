package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published;

import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 未发布的协议接口
 */

interface UnPublishedContract {

    interface View extends BaseListView<UnPublishedPresenter, QuestionnaireItemInfo> {

        /**
         * 分页获取数据
         *
         * @param currentPage  当前页
         */
        void getItemInfo(int currentPage);
    }


    interface Presenter extends BasePresenter {

    }
}
