package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import com.zfsoftmh.entity.AppCenterInfo;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 订阅中心协议接口
 */

interface SubscriptionCenterContract {


    interface View extends BaseView<SubscriptionCenterPresenter> {

        /**
         * 获取应用中心的数据
         */
        void getAppCenterInfo();

        /**
         * 服务获取成功
         *
         * @param list 服务集合
         */
        void loadSuccess(ArrayList<AppCenterItemInfo> list);

        /**
         * 服务获取失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 显示对话框
         *
         * @param msg 提示信息
         */
        void showDialog(String msg);

        /**
         * 隐藏对话框
         */
        void hideDialog();

        /**
         * 服务提交成功
         */
        void serviceSubmitSuccess();

        /**
         * 服务提交失败
         *
         * @param errorMsg 失败的信息
         */
        void serviceSubmitFailure(String errorMsg);

        /**
         * 判断是否是编辑状态
         *
         * @param title 标题
         * @return true: 编辑状态 false：不是编辑状态
         */
        boolean checkIsEditMode(String title);

        /**
         * 跟新menu的值
         *
         * @param title 标题
         */
        void updateMenuItemTitle(String title);

        /**
         * 判断是否有选择服务
         *
         * @param list 服务集合
         * @return true: 有 false: 没有
         */
        boolean checkHasService(ArrayList<AppCenterItemInfo> list);

        /**
         * 对象类型转化为string
         *
         * @param list 集合
         * @return 对象
         */
        String objectToString(List<String> list);

        /**
         * 获取用户登录时的id
         *
         * @return 用户id
         */
        String getUserId();
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取应用中心的数据
         */
        void getAppCenterInfo();

        /**
         * 处理数据
         *
         * @param data 服务器返回的数据
         */
        void dealData(List<AppCenterInfo> data);

        /**
         * 提交服务
         *
         * @param servicecode 服务编码
         */
        void submitService(String servicecode);
    }
}
