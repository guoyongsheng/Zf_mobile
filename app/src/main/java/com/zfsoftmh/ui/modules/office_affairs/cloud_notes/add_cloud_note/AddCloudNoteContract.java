package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import android.graphics.Bitmap;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间： 2017/5/3 0003
 * 编写人：
 * 功能描述：
 */

public interface AddCloudNoteContract {
    interface View extends BaseView<AddCloudNotePresenter> {

        /**
         * 创建权限请求对话框
         */
        void createAppSettingDialog();

        /**
         * 获取笔记标题
         */
        String getNoteTitle();

        /**
         * 获取笔记内容
         */
        String getNoteContent();

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
         * 上传图片
         */
        void uploadPic(File file, Bitmap bitmap);

        /**
         * 上传笔记
         */
        void uploadNote();

        /**
         * 获取屏幕宽度
         */
        void getScreenWidth();

        /**
         * 获取要上传的文件
         *
         * @param partName 名字
         * @param file     文件
         * @return 要上传的文件
         */
        MultipartBody.Part getFilePart(String partName, File file,String fileName);

        /**
         * 获取笔记文件名
         */
        void getNoteFileName();

        /**
         * 上传失败
         *
         * @param errMsg 失败的信息
         */
        void upLoadFailure(String errMsg);

        /**
         * 向视图插入图片显示
         */
        void insertViewPic(Bitmap bitmap, String picUrl);

        /**
         * 删除拍照后图片
         */
        void deleteCameraPic();

        /**
         * 删除本地新建笔记
         */
        void deleteCloudNote();

        /**
         * 撤销以及添加图片按钮可见于不可见
         */
        void repealIconShow();

        /**
         * 笔记上传成功之后跳转
         */
        void skipPage();

        /**
         * 布局生成图片
         */
        void generateImg();

        /**
         * 保存分享图片
         */
        void savePicture(Bitmap bm, String fileName);

    }

    interface Presenter extends BasePresenter {

        /**
         * 云笔记上传图片
         */
        void uploadPicture(MultipartBody.Part file, Bitmap bitmap,RequestBody apptoken);

        /**
         * 上传云笔记
         */
        void uploadCloudNote(MultipartBody.Part file, Map<String, RequestBody> params);

    }

}
