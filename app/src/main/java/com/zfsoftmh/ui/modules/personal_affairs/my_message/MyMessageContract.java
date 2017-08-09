package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import com.zfsoftmh.entity.MyMessageItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public interface MyMessageContract {

    interface View extends BaseListView<MyMessagePresenter, MyMessageItemInfo> {
        /**
         * 获取收藏列表数据
         *
         * @param start_page 起始页
         * @param PAGE_SIZE  每页条数
         */
        void loadMyMessageList(int start_page, int PAGE_SIZE);

        /**
         * 消息列表加载成功
         *
         * @param data
         */
        void loadMyMessageListSuccess(ResponseListInfo<MyMessageItemInfo> data);
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取我的消息列表
         *
         * @param params
         */
        void getMyMessageList(Map<String, String> params);
    }
}
