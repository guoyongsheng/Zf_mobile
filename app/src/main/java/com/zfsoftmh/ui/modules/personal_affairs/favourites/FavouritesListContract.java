package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import com.zfsoftmh.entity.FavouritesInfo;
import com.zfsoftmh.entity.FavouritesListInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description:
 */

interface FavouritesListContract {
    interface View extends BaseListView<FavouritesListPresenter, FavouritesListInfo> {

        /**
         * 数据加载框
         *
         * @param msg
         */
        void createLoadingDialog(String msg);

        /**
         * 隐藏加载框
         */
        void hideUpLoadingDialog();

        /**
         * 获取收藏列表数据
         *
         * @param start_page 起始页
         * @param PAGE_SIZE  每页条数
         */
        void loadFavouritesList(int start_page, int PAGE_SIZE);

        /**
         * 加载失败提示框
         *
         * @param msg
         */
        void loadFavouritesFailure(String msg);

        /**
         * 设置收藏列表数据
         *
         * @param data
         */
        void setFavouritesData(ResponseListInfo<FavouritesListInfo> data);

        /**
         * 删除收藏数据成功回调
         */
        void deleteFavouritesSuccess(FavouritesListInfo info);


    }

    interface Presenter extends BasePresenter {

        /**
         * 加载收藏列表
         */
        void loadFavouritesList(Map<String, String> params);

        /**
         * 删除收藏数据
         *
         * @param favourId 收藏ID
         */
        void deleteFavourites(String favourId, FavouritesListInfo info);


    }
}
