package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice;

import com.zfsoftmh.entity.DisCoveryTypeInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 寻物启事协议接口
 */

interface LookingForNoticeContract {

    interface View extends BaseView<LookingForNoticePresenter> {

        /**
         * 提交
         */
        void submit();

        /**
         * 获取标题
         *
         * @return 标题
         */
        String getTitle();

        /**
         * 获取地点
         *
         * @return 地点
         */
        String getLocation();

        /**
         * 获取详情
         *
         * @return 详情
         */
        String getDetail();

        /**
         * 判断数据是否为空
         *
         * @param value 数据
         * @return true: 为空 false: 不为空
         */
        boolean checkValueIsNull(String value);

        /**
         * 提交成功
         */
        void submitSuccess();

        /**
         * 提交失败
         *
         * @param errorMsg 失败信息
         */
        void submitFailure(String errorMsg);

        /**
         * 显示对话框
         *
         * @param msg 要显示的信息
         */
        void showProgress(String msg);

        /**
         * 隐藏对话框
         */
        void hideProgress();

        /**
         * 结束界面
         */
        void finish();

        /**
         * 检查是否应该有图片
         *
         * @return true: 有 false： 没有
         */
        boolean checkShouldHasImage();

        /**
         * 获取招领类型
         *
         * @return DisCoveryTypeInfo对象
         */
        DisCoveryTypeInfo getDisCoveryTypeInfo();

        /**
         * 打开
         */
        void openActivity();

        /**
         * 创建文件
         *
         * @return 文件
         */
        File createNewFile();

        /**
         * 判断文件是否创建成功
         *
         * @return true: 成功 false: 失败
         */
        boolean checkFileIsCreateSuccess();

        /**
         * 跟新界面的recyclerView
         */
        void updateRecycler();


        /**
         * 获取手机号码
         *
         * @return 手机号码
         */
        String getPhoneNumber();

        /**
         * 构造参数
         *
         * @param title     标题
         * @param place     地点
         * @param content   描述
         * @param telephone 手机号
         * @param flag      1:失物招领 0:寻物启示
         * @return map
         */
        Map<String, RequestBody> buildParams(String title, String place, String content, String telephone,
                int flag);

        /**
         * RequestBody
         *
         * @param value     值
         * @param isEncoded 是否加密
         * @param apptoken  登录凭证
         * @return RequestBody
         */
        RequestBody getRequestBody(String value, boolean isEncoded, String apptoken);

        /**
         * 构建map
         *
         * @param listPath 文件路径
         * @return map
         */
        Map<String, RequestBody> buildParams(List<String> listPath);
    }


    interface Presenter extends BasePresenter {

        /**
         * 提交事务招领
         *
         * @param map  额外参数
         * @param file 文件
         */
        void submit(Map<String, RequestBody> map, Map<String, RequestBody> file);
    }
}
