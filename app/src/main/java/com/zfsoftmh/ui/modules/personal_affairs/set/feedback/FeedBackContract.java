package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

/**
 * @author wesley
 * @date: 2017/3/24
 * @Description: 意见反馈的协议接口
 */

public interface FeedBackContract {

    interface View extends BaseView<FeedBackPresenter> {


        /**
         * 显示信息
         *
         * @param errorMsg 错误信息
         */
        void showErrorMsg(String errorMsg);

        /**
         * 反馈成功
         */
        void feedBackSuccess();

    }


    interface Presenter extends BasePresenter {

        /**
         * 校验反馈内容是否为空
         *
         * @param content 反馈内容
         * @return true: 为空 false: 不为空
         */
        boolean checkFeedBackContentIsEmpty(String content);

        /**
         * 意见反馈
         *
         * @param content 反馈内容
         */
        void feedBack(String content);
    }
}
