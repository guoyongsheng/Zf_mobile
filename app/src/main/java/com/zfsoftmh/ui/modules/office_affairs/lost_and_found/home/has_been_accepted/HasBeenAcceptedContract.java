package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted;

import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 已招领协议接口
 */

interface HasBeenAcceptedContract {


    interface View extends BaseListView<HasBeenAcceptedPresenter, LostAndFoundItemInfo> {

    }


    interface Presenter extends BasePresenter {

        /**
         * 分页获取数据
         *
         * @param start    起始页
         * @param pageSize 每页请求多少条数据
         * @param type     0:未招领 1:已招领
         * @param apptoken 登录凭证
         */
        void loadData(int start, int pageSize, int type, String apptoken);
    }
}
