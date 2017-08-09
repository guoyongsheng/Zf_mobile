package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import android.graphics.Bitmap;

import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description:
 */

public interface NoteDetailContract {
    interface View extends BaseView<NoteDetailPresenter> {
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
        MultipartBody.Part getFilePart(String partName, File file, String fileName);

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

        /**
         * 设置数据
         */
        void setData();

        /**
         * 下载文件
         */
        void downloadFile();

        /**
         * 新建目录
         */
        void saveDir();

        /**
         * 获取下载笔记文件
         *
         * @param s 下载笔记内容
         */
        void getDownloadFileContent(String s);

        /**
         * 加载文字
         *
         * @param s 文本内容
         */
        void loadText(String s);

        /**
         * 加载图片
         *
         * @param s 图片下载地址
         */
        void loadImage(String s);

        /**
         * 获取压缩图片
         *
         * @param file
         * @param startIndex  图片占位符起始位置
         * @param endIndex    图片占位符末尾位置
         * @param placeholder 占位符
         * @return
         */
        void compressImage(File file, int startIndex, int endIndex, String placeholder);

        /**
         * 显示下载图片
         *
         * @param bitmap      显示图片
         * @param startIndex  图片占位符起始位置
         * @param endIndex    图片占位符末尾位置
         * @param placeholder 占位符
         */
        void showDownloadImage(Bitmap bitmap, int startIndex, int endIndex, String placeholder);

        /**
         * 存入本地磁盘动态权限请求
         */
        void readDiskPermission();
    }

    interface Presenter extends BasePresenter {

        /**
         * 云笔记上传图片
         */
        void uploadPicture(MultipartBody.Part file, Bitmap bitmap, RequestBody apptoken);

        /**
         * 上传云笔记
         */
        void uploadCloudNote(MultipartBody.Part file, Map<String, RequestBody> params);

        /**
         * 下载笔记
         *
         * @param downloadPath 下载地址
         * @param fileName     文件名
         */
//        void downloadCloudNote(String downloadPath, String fileName);

        /**
         * 下载笔记
         *
         * @param downloadPath
         */
        void downloadNote(String downloadPath);

        /**
         * 下载图片
         *
         * @param downloadPath 图片下载地址
         * @param startIndex   图片占位符起始位置
         * @param endIndex     图片占位符末尾位置
         * @param fileName     文件名
         * @param placeholder  占位符
         */
        void downloadImage(String downloadPath, int startIndex, int endIndex, String fileName, String placeholder);

    }
}
