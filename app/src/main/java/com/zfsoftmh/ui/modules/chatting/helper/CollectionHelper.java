package com.zfsoftmh.ui.modules.chatting.helper;

import android.content.Context;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;
import com.zfsoftmh.ui.base.BaseApplication;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sy
 * on 2017/6/21.
 * <p>收藏工具</p>
 */

public class CollectionHelper {

    private String username;
    private String appToken;
    private String favouritesSort;//类型 -- 1文本 2图片3视频4网址 5附件
    private String favouritesCustom;//作者
    private String favouritesAvatar;//作者头像
    private String favouriteTitle;//标题
    private String favouritesContent;//内容
    private MultipartBody.Part uploadPart;//上传内容 -- 如果是网址 这里就是缩略图
    private CallBackListener<String> callback;
    private String favouritesattachmentsort;//附件种类，如doc、pdf等。

    public static final String TYPE_TEXT = "1";
    public static final String TYPE_IMAGE = "2";
    public static final String TYPE_VIDEO = "3";
    public static final String TYPE_URL = "4";
    public static final String TYPE_FILE = "5";

    public void sendRequest(Context context){
        username = DbHelper.getUserId(context);
        appToken = DbHelper.getAppToken(context);
        Map<String, RequestBody> params = getParams();
        HttpManager httpManager = BaseApplication.getAppComponent().getHttpHelper();
        PersonalAffairsApi affairsApi = BaseApplication.getAppComponent().getRetrofitForLogin()
                .create(PersonalAffairsApi.class);
        httpManager.outRequest(affairsApi.submitFavouritesData(params, uploadPart), callback);
    }

    private Map<String, RequestBody> getParams() {
        Map<String, RequestBody> params = new LinkedHashMap<>();
        params.put("apptoken", getRequestBody(appToken));
        params.put("username", getRequestBody(username, appToken));
        params.put("favouritesAvatar", getRequestBody(favouritesAvatar, appToken));
        params.put("favouritesCustom", getRequestBody(favouritesCustom, appToken));
        params.put("favouritetitle", getRequestBody(favouriteTitle, appToken));
        params.put("favouritesContent", getRequestBody(favouritesContent, appToken));
        params.put("favouritesattachmentsort", getRequestBody(favouritesattachmentsort, appToken));
        params.put("favouritesSort", getRequestBody(favouritesSort, appToken));
        return params;
    }


    private RequestBody getRequestBody(String s, String appToken) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT),
                CodeUtil.getEncodedValueWithToken(s, appToken));
    }

    private RequestBody getRequestBody(String s) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), s);
    }

    public static class ParamsBuilder {

        private CollectionHelper collectionHelper;

        public ParamsBuilder() {
            collectionHelper = new CollectionHelper();
        }

        public ParamsBuilder setSort(String type){
            collectionHelper.favouritesSort = type;
            return this;
        }

        public ParamsBuilder setAuthor(String author) {
            collectionHelper.favouritesCustom = author;
            return this;
        }

        public ParamsBuilder setAvatarPath(String path) {
            collectionHelper.favouritesAvatar = path;
            return this;
        }

        public ParamsBuilder setFileType(String type) {
            collectionHelper.favouritesattachmentsort = type;
            return this;
        }

        public ParamsBuilder setTitle(String title) {
            collectionHelper.favouriteTitle = title;
            return this;
        }

        public ParamsBuilder setContent(String content) {
            collectionHelper.favouritesContent = content;
            return this;
        }

        public ParamsBuilder setFile(File file, String fileName) {
            collectionHelper.uploadPart = getFilePart(file, fileName);
            return this;
        }

        private MultipartBody.Part getFilePart(File file, String fileName) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file);
            return MultipartBody.Part.createFormData("favouritesContent", fileName, requestBody);
        }

        public CollectionHelper build(CallBackListener<String> callback){
            collectionHelper.callback = callback;
            return collectionHelper;
        }

    }


}
