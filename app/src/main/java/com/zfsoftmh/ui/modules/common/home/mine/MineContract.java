package com.zfsoftmh.ui.modules.common.home.mine;

import com.zfsoftmh.entity.MyPortalInfo;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 我的界面的接口
 */

interface MineContract {

    interface View extends BaseView<MinePresenter> {

        /**
         * 创建对话框
         */
        void createAppSettingDialog();

        /**
         * 加载数据
         */
        void loadData();

        /**
         * 判读用户是否登录
         *
         * @return true: 登录  false: 未登录
         */
        boolean checkUserIsLogin();

        /**
         * 我的门户数据加载成功
         *
         * @param data MyPortalInfo对象
         */
        void loadSuccess(MyPortalInfo data);

        /**
         * 加载失败
         *
         * @param errorMsg 失败的信息
         */
        void loadFailure(String errorMsg);

        /**
         * 创建上传头像的对话框
         */
        void createUpLoadIconDialog();

        /**
         * 裁剪图片
         *
         * @param file 图片保存的位置
         */
        void cropImage(File file);

        /**
         * 上传文件
         *
         * @param file 要上传的文件
         */
        void upLoadFile(File file);

        /**
         * 获取要上传的文件
         *
         * @param partName 名字
         * @param file     文件
         * @return 要上传的文件
         */
        MultipartBody.Part getFilePart(String partName, File file);

        /**
         * 获取上传头像时携带的参数
         *
         * @param userId    用户id
         * @param isEncoded 是否加密 true: 加密 false: 不加密
         * @return RequestBody对象
         */
        RequestBody getRequestBody(String userId, boolean isEncoded);

        /**
         * 上传失败
         *
         * @param errMsg 失败的信息
         */
        void upLoadFailure(String errMsg);

        /**
         * 上传成功
         *
         * @param msg 成功的信息
         */
        void upLoadSuccess(String msg);

        /**
         * 跟新头像
         *
         * @param path 头像路径
         */
        void updateIcon(String path);

        /**
         * 获取用户id
         *
         * @return 用户id
         */
        String getUserId();

        /**
         * 获取用户登录信息
         *
         * @return User对象
         */
        User getUserInfo();

        /**
         * 创建文件
         *
         * @return 裁剪后图片的保存位置
         */
        File createNewFile();

        /**
         * 判断文件是否创建成功
         *
         * @return true: 创建成功  false: 创建失败
         */
        boolean checkFileIsCreateSuccess();

        /**
         * 显示上传图片的对话框
         *
         * @param upLoad 上传信息
         */
        void showUpLoadDialog(String upLoad);

        /**
         * 隐藏对话框
         */
        void hideUpLoadDialog();

        /**
         * 从相册中选取
         */
        void openActivity();

        /**
         * 初始化headerView
         */
        void initHeaderView();

        /**
         * 获取用户名
         *
         * @return 用户名
         */
        String getUserName();

        /**
         * 获取头像路径
         *
         * @return 头像路径
         */
        String getHeaderPath();

        /**
         * 初始化item
         */
        void initItem();

        /**
         * 保存用户信息
         * @param path 用户头像
         */
        void saveUserInfo(String path);

        /**
         * 扫一扫
         */
        void scanRQCode();

        void singInSuccess(String integralNumber);

        /**
         * 保存积分值
         */
        void integralSave();
    }

    interface Presenter extends BasePresenter {

        /**
         * 加载我的门户的数据
         */
        void loadData();


        /**
         * 上传文件
         *
         * @param file 要上传的文件
         */
        void upLoadFile(RequestBody requestBody, RequestBody apptoken, MultipartBody.Part file, String path);

        /**
         * 签到
         * @param source 签到一次所加积分
         * @param appSource 签到来源
         */
        void signIn(String source,String appSource);
    }
}
