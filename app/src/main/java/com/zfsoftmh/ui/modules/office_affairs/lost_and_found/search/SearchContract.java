package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 失物招领---搜索协议接口
 */

interface SearchContract {

    interface View extends BaseListView<SearchPresenter, LostAndFoundItemInfo> {

        /**
         * 检查标题是否为空
         *
         * @param title 标题
         * @return true: 空 false: 不为空
         */
        boolean checkTitleIsNull(String title);

        /**
         * 显示对话框
         *
         * @param msg 对话框要显示的信息
         */
        void showDialog(String msg);

        /**
         * 隐藏对话框
         */
        void hideDialog();
    }

    interface Presenter extends BasePresenter {

        /**
         * 分页获取数据
         *
         * @param start    起始页
         * @param pageSize 每页请求多少条数据
         * @param type     0:未招领 1:已招领
         * @param title    搜索内容
         * @param userId   用户登录id
         * @param apptoken 登陆凭证
         */
        void loadData(int start, int pageSize, String title, int type, String userId, String apptoken);
    }
}
