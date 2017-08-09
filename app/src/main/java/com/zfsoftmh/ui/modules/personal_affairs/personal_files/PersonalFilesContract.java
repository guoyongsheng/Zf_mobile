package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import com.zfsoftmh.entity.PersonalFilesInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description:
 */

public interface PersonalFilesContract {

    interface View extends BaseView<PersonalFilesPresenter> {

        /**
         * 加载数据
         */
        void loadData();

        /**
         * 获取用户登录的id
         *
         * @return 用户id
         */
        String getUserId();

        /**
         * 显示错误的信息
         *
         * @param errorMsg 错误的信息
         */
        void showErrorMsg(String errorMsg);

        /**
         * 显示数据
         *
         * @param data 个人档案集合
         */
        void showData(List<PersonalFilesInfo> data);

        /**
         * 获取个人档案详情
         *
         * @param id      itemId
         * @param account 账号
         */
        void loadDetailData(String id, String account);

        /**
         * 显示详情信息
         *
         * @param data 个人档案详情信息
         */
        void showDetailData(List<List<Map<String, String>>> data, int position);
    }


    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         */
        void loadData();


        /**
         * 获取个人档案详情
         *
         * @param id       itemId
         * @param account  账号
         * @param position 点击的位置
         */
        void loadDetailData(String id, String account, int position);
    }
}
