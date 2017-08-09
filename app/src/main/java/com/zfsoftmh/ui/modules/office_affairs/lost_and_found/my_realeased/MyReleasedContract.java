package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 我的发布界面的协议接口
 */

interface MyReleasedContract {


    interface View extends BaseListView<MyReleasedPresenter, LostAndFoundItemInfo> {

    }


    interface Presenter extends BasePresenter {

        /**
         * 分页获取数据
         *
         * @param start    起始页
         * @param pageSize 每页请求多少条数据
         * @param type     0:未招领 1:已招领
         * @param userId   用户登录id
         * @param apptoken 登陆凭证
         */
        void loadData(int start, int pageSize, int type, String userId, String apptoken);
    }
}
