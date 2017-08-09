package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import android.graphics.Bitmap;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import java.io.File;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description:
 */

public class NoteDetailPresenter implements NoteDetailContract.Presenter {
    private NoteDetailContract.View view;
    private OfficeAffairsApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    public NoteDetailPresenter(NoteDetailContract.View view, OfficeAffairsApi commonApi, HttpManager httpManager) {
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

    @Override
    public void uploadPicture(MultipartBody.Part file, final Bitmap bitmap, RequestBody apptoken) {
        view.createUpLoadPicDialog(Constant.PICTURE_UPLOADING);
        httpManager.request(commonApi.upLoadCloudNotePic(apptoken, file),
                compositeDisposable, view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideUpLoadPicDialog();
                        view.insertViewPic(bitmap, data);
                        view.deleteCameraPic();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadPicDialog();
                        view.upLoadFailure(Constant.PICTURE_UPLOAD_FAILURE);
                    }
                });
    }

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

    @Override
    public void downloadNote(String downloadPath) {
        downloadPath = downloadPath + "?time=" + System.currentTimeMillis();
        commonApi.downLoadFile(downloadPath)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, Response<ResponseBody>>() {
                    @Override
                    public Response<ResponseBody> apply(@NonNull Response<ResponseBody> response) throws Exception {
                        return response;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(@NonNull Response<ResponseBody> s) throws Exception {
                        String content = "";
                        if (s != null && s.isSuccessful()) {
                            content = CloudNoteUtils.downTxt(s.body());
                            if (content != null && !content.equals("")) {
                                view.getDownloadFileContent(content);
                            } else {
                                view.upLoadFailure(Constant.note_list_failure);
                            }
                        } else {
                            view.upLoadFailure(Constant.note_list_failure);
                        }

                    }
                });
    }

    @Override
    public void downloadImage(String downloadPath, final int startIndex, final int endIndex, final String fileName, final String placeholder) {
        downloadPath = downloadPath + "?time=" + System.currentTimeMillis();
        commonApi.downLoadFile(downloadPath)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, Response<ResponseBody>>() {
                    @Override
                    public Response<ResponseBody> apply(@NonNull Response<ResponseBody> response) throws Exception {
                        return response;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(@NonNull Response<ResponseBody> response) throws Exception {
                        if (response != null && response.isSuccessful()) {
                            boolean writtenToDisk = CloudNoteUtils.writeResponseBodyToDisk(response.body(), fileName);
                            if (writtenToDisk) {
                                File file = new File(fileName);
                                if (file != null) {
                                    view.compressImage(file, startIndex, endIndex, placeholder);
                                } else {
                                    view.upLoadFailure(Constant.image_load_failure);
                                }
                            } else {
                                view.upLoadFailure(Constant.image_load_failure);

                            }

                        } else {
                            view.upLoadFailure(Constant.image_load_failure_delete);
                        }

                    }
                });
    }
}
