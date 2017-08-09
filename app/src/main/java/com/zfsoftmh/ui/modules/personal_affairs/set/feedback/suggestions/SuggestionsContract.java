package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description:
 */

public interface SuggestionsContract {
    interface View extends BaseView<SuggestionsPresenter> {
        /**
         * 创建权限请求对话框
         */
        void createAppSettingDialog();

        /**
         * 创建添加图片对话框
         */
        void createAddPicDialog();

        /**
         * 打开本地相册请求
         */
        void getImageFromAlbum();

        /**
         * 拍照请求
         */
        void getImageFromCamera();

        /**
         * 拍照图片存储地址
         */
        void getImageFromCameraPath();

        /**
         * 获取压缩后图片
         */
        void getCompressImage(File file);

        /**
         * 创建上传图片加载框
         *
         * @param upLoad 上传信息
         */
        void createUpLoadPicDialog(String upLoad);

        /**
         * 隐藏对话框
         */
        void hideUpLoadPicDialog();

        /**
         * 获取要上传的文件
         *
         * @param partName 名字
         * @param file     文件
         * @return 要上传的文件
         */
        MultipartBody.Part getFilePart(String partName, File file, String fileName);

        /**
         * 上传失败
         *
         * @param errMsg 失败的信息
         */
        void upLoadFailure(String errMsg);

        /**
         * 删除拍照后图片
         */
        void deleteCameraPic();

        /**
         * 笔记上传成功之后跳转
         */
        void skipPage();

        /**
         * 获取问题与意见内容
         *
         * @return
         */
        String getComments();

        /**
         * 获取联系电话
         *
         * @return
         */
        String getTel();

        /**
         * 获取QQ
         *
         * @return
         */
        String getQQ();

        /**
         * 提交反馈内容
         */
        void commitSuggestions();

    }

    interface Presenter extends BasePresenter {

        /**
         * 上传意见反馈
         *
         * @param requestBody string参数
         * @param images       文件参数
         */
        void uploadSuggestions(Map<String, RequestBody> requestBody, List<MultipartBody.Part> images);
    }
}
