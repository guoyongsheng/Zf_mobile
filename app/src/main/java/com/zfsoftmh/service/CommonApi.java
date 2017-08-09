package com.zfsoftmh.service;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.entity.VersionInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 通用模块的所有接口都放在这里
 */
public interface CommonApi {

    /**
     * 安装统计接口
     *
     * @return String类型的数据
     */
    @GET("zftal-mobile/commonHttp/commonHttp_installsCount.html")
    Observable<Response<String>> install();

    /**
     * 登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return Response<User>对象
     */
    @FormUrlEncoded
    @POST("zftal-mobile/commonHttp/commonHttp_login.html")
    Observable<Response<User>> login(@Field("username") String username, @Field("password")
            String password, @Field("strKey") String strKey);


    /**
     * 版本校验
     *
     * @param map 所有的请求参数都封装在map中
     * @return VersionInfo 对象
     */
    @GET(Config.URL.BASE_URL_VERSION_CHECK + "zfmobile_versionMB/verify/versionCompare")
    Observable<Response<VersionInfo>> checkVersion(@QueryMap Map<String, String> map);

    /**
     * 上传意见反馈
     *
     * @param requestBody 意见内容等参数集合
     * @param images      图片文件参数集合
     * @return
     */
    @Multipart
    @POST(Config.URL.BASE_URL_CORPORATION + "zftal-mobile/commonHttp/commonHttp_submitSuggest.html")
    Observable<Response<String>> upLoadFeedBack(@PartMap Map<String, RequestBody> requestBody,
                                                @Part List<MultipartBody.Part> images);
}
