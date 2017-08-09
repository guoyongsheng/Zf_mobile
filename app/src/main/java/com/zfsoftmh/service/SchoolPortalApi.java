package com.zfsoftmh.service;

import com.zfsoftmh.entity.AppCenterInfo;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.BannerInfo;
import com.zfsoftmh.entity.HomeNewListInfo;
import com.zfsoftmh.entity.HomeTitleTabInfo;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.entity.SchoolMapInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 学校门户模块的所有接口---门户模块包括: 概况、风景、院系、师资、新闻、应用中心
 */
public interface SchoolPortalApi {

    /**
     * 获取应用中心的数据 缓存一天
     *
     * @return 应用中心对象
     */
    @Headers("Cache-Control: public, max-age=" + 60 * 60 * 24)
    @GET("zftal-mobile/appCenter/appCenter_getALLXtYwByUser.html")
    Observable<Response<List<AppCenterInfo>>> getAppCenterInfo();

    /**
     * 获取首页轮播图
     *
     * @param size 最大的数量
     * @return 轮播图集合
     */
    @GET("zftal-mobile/homePageHttp/homePageHttp_getMhRecommendPage.html")
    Observable<Response<List<BannerInfo>>> getBanner(@Query("size") int size);

    /**
     * 获取未登录时的首页服务
     *
     * @return 首页服务集合
     */
    @GET("zftal-mobile/homePageHttp/homePageHttp_getCommonService.html")
    Observable<Response<ArrayList<AppCenterItemInfo>>> getHomeServiceInfoWithNoLogin();


    /**
     * 获取登录时的首页服务
     *
     * @return 首页服务集合
     */
    @GET("zftal-mobile/homePageHttp/homePageHttp_Commonfunction.html")
    Observable<Response<ArrayList<AppCenterItemInfo>>> getHomeServiceInfoWithLogin();

    /**
     * @param strKey 常量值
     * @return 首页标题的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/getNewsTab")
    Observable<Response<List<HomeTitleTabInfo>>> getHomeTitleInfo(@Query("strKey") String strKey);

    /**
     * 订阅中心获取更多服务
     *
     * @return 服务的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/CommonOtherFunction")
    Observable<Response<ArrayList<AppCenterItemInfo>>> getMoreServiceInfo();

    /**
     * 订阅中心 提交服务
     *
     * @param data 服务编码
     * @return Response<String>
     */
    @FormUrlEncoded
    @POST("zftal-mobile/homePageHttp/homePageHttp_SubmitCommonFunction.html")
    Observable<Response<String>> submitService(@Field("data") String data);

    /**
     * 获取首页新闻列表
     *
     * @param id    类型id
     * @param start 起始页
     * @param size  每页请求多少条数据
     * @return 新闻列表的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/getNewsList")
    Observable<Response<List<HomeNewListInfo>>> getHomeNewListInfo(@Query("id") String id,
            @Query("start") int start, @Query("size") int size);


    /**
     *
     * @return地图学校校区信息
     */

    @GET("zftal-mobile/appCenter/appCenter_getMapList.html")
    Observable<Response<ArrayList<SchoolMapInfo>>>getSchoolMapInfo();


}
