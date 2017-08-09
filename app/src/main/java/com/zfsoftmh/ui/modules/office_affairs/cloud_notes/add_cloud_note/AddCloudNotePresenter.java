package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import android.graphics.Bitmap;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间： 2017/5/2 0002
 * 编写人： 王世美
 * 功能描述： 新建笔记业务逻辑
 */

public class AddCloudNotePresenter implements AddCloudNoteContract.Presenter {
    private AddCloudNoteContract.View view;
    private OfficeAffairsApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    public AddCloudNotePresenter(AddCloudNoteContract.View view, OfficeAffairsApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);

    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    /**
     * 云笔记上传图片
     */
    @Override
    public void uploadPicture(MultipartBody.Part file, final Bitmap bitmap,RequestBody apptoken) {
        view.createUpLoadPicDialog(Constant.PICTURE_UPLOADING);
        httpManager.request(commonApi.upLoadCloudNotePic(apptoken,file),
                compositeDisposable, view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideUpLoadPicDialog();
                        view.insertViewPic(bitmap,data);
                        view.deleteCameraPic();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadPicDialog();
                        view.upLoadFailure(Constant.PICTURE_UPLOAD_FAILURE);
                    }
                });
    }

    /**
     * 上传云笔记
     */
    @Override
    public void uploadCloudNote(MultipartBody.Part file, Map<String, RequestBody> params) {
        view.createUpLoadPicDialog(Constant.note_uploading);
        httpManager.request(commonApi.upLoadCloudNote(params, file), compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.hideUpLoadPicDialog();
                view.deleteCloudNote();
                view.skipPage();
            }

            @Override
            public void onError(String errorMsg) {
                view.hideUpLoadPicDialog();
                view.upLoadFailure(Constant.note_upload_failure);
            }
        });

    }


}
