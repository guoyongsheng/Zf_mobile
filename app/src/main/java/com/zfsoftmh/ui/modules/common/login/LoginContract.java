package com.zfsoftmh.ui.modules.common.login;

import com.zfsoftmh.entity.User;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 登录的协议接口
 */
public interface LoginContract {

    interface View extends BaseView<LoginPresenter> {

        /**
         * 获取用户的信息
         *
         * @return 用户最后一次登录的信息
         */
        User getUserInfo();

        /**
         * 获取所有用户id的集合
         *
         * @return List<String>
         */
        List<String> getUserIdList();

        /**
         * 插入数据
         */
        void copyToRealmOrUpdate(User user);


        /**
         * 判断当前用户是否在列表中
         *
         * @param user 用户登录对象
         * @param list 列表
         * @return true: 在 false: 不在
         */
        boolean checkUserIsInList(User user, List<User> list);


        /**
         * 查询所有的用户
         *
         * @return 用户集合
         */
        List<User> queryAllUser();

        /**
         * 初始化id和头像
         */
        void initIdAndHeaderPath(User user, int type);

        /**
         * 获取用户名
         *
         * @return 用户名
         */
        String getUserName();

        /**
         * 获取密码
         *
         * @return 密码
         */
        String getPassword();

        /**
         * 登录
         */
        void login();

        /**
         * 显示错误的信息
         *
         * @param errorMsg 错误的信息
         */
        void showErrorMsg(String errorMsg);

        /**
         * 登录成功
         *
         * @param user user全局的对象
         */
        void loginSuccess(User user);
    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         *
         * @param userName 用户名
         * @param password 密码
         * @param key      这是一个常量值
         */
        void login(String userName, String password, String key);

        /**
         * 校验用户输入的值是否为空
         *
         * @param data 用户名或者密码
         * @return true: 空  false:不为空
         */
        boolean checkDataIsEmpty(String data);
    }
}
