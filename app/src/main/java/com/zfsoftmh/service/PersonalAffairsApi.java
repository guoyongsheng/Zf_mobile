package com.zfsoftmh.service;

import com.zfsoftmh.entity.DigitalFileDepartInfo;
import com.zfsoftmh.entity.DigitalFileItemInfo;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.entity.FavouritesListInfo;
import com.zfsoftmh.entity.FoodCataListInfo;
import com.zfsoftmh.entity.IntegralIncomeItemInfo;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.entity.IntegralRankingInfo;
import com.zfsoftmh.entity.MyMessageItemInfo;
import com.zfsoftmh.entity.MyPortalInfo;
import com.zfsoftmh.entity.OneCardInfo;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.entity.OrderForminfo;
import com.zfsoftmh.entity.PersonalFilesInfo;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.pay.entity.SignInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 个人事务模块的所有接口 ---个人事务包括 邮件、一卡通、个人财务、通讯录、社区、我的
 */
public interface PersonalAffairsApi {

    /**
     * 获取我的门户的信息
     *
     * @return 我的门户集合
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_sourceFunctionForPortal.html")
    Observable<Response<MyPortalInfo>> getMyPortalInfo();

    /**
     * 上传头像
     * <p>
     * 多文件上传的格式 @PartMap Map<String, RequestBody> map
     * map.put("file" + "\"; + filename=\"" + "111", RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file))
     *
     * @param file     头像路径
     * @param userId   用户id
     * @param apptoken 登录凭证
     * @return Response<String>对象
     */
    @Multipart
    @POST("zftal-mobile/myPortalHttp/myPortalHttp_uploadMyheadPic.html")
    Observable<Response<String>> upLoadUserIcon(@Part("username") RequestBody userId, @Part("apptoken") RequestBody apptoken, @Part MultipartBody.Part file);

    /**
     * 添加收藏
     *
     * @param file 文件
     * @return Response<String>对象
     */
    @Multipart
    @POST("zftal-mobile/appCenter/appCenter_submitFavouritesData.html")
    Observable<Response<String>> submitFavouritesData(
            @PartMap Map<String, RequestBody> requestBody,
            @Part MultipartBody.Part file);

    /**
     * 上传文件
     *
     * @return 下载地址
     */
    @Multipart
    @POST("zftal-mobile/commonHttp/commonHttp_uploadttachment.html")
    Observable<Response<ArrayList<String>>> postMediaFile(@Part("apptoken") RequestBody apptoken, @Part MultipartBody.Part file);

    /**
     * 获取个人档案信息
     *
     * @return 个人档案对象集合
     */
    @GET("zftal-mobile/commonHttp/commonHttp_personDocumentInformationList.html")
    Observable<Response<List<PersonalFilesInfo>>> getPersonalFilesInfo();

    /**
     * 获取个人档案详情
     *
     * @param id      item id
     * @param account 用户登录id
     * @return 详情信息
     */
    @GET("zftal-mobile/commonHttp/commonHttp_personDocumentInformation.html")
    Observable<Response<List<List<Map<String, String>>>>> getPersonalFilesDetailInfo(@Query("informationId") String id, @Query("informationName") String account);

    /**
     * 一卡通 --- 获取账户余额
     *
     * @return OneCardInfo对象
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getocbalance.html")
    Observable<Response<OneCardInfo>> getAccountBalance();

    /**
     * 分页获取消费明细和充值明细的数据
     *
     * @param startPage 起始页
     * @param pageSize  每页加载多少条数据
     * @param oneCardId 一卡通的id
     * @param type      1: 消費明細   0: 充值明細
     * @return 一卡通详情列表
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getocdetail.html")
    Observable<Response<ResponseListInfo<OneCardItemDetailsInfo>>> getOneCardDetailsInfo(@Query("pageindex")
                                                                                                 int startPage, @Query("pagesize") int pageSize, @Query("ocid") String oneCardId, @Query("detailtype") String type);

    /**
     * 一卡通充值--- 获取签名
     *
     * @param orderInfo 订单信息
     * @return 签名信息
     */
    @GET("zftal-mobile/servlet/NotifyServlet/getSign")
    Observable<Response<SignInfo>> getSign(@Query("data") String orderInfo, @Query("biz_content_android") String biz_content_android);


    /**
     * 获取数字档案的部门类型
     *
     * @return 部门类型集合
     */
    @GET("zftal-mobile/webservice/newmobile/MobileLoginXMLService/digitalArchivesDpList")
    Observable<Response<List<DigitalFileDepartInfo>>> getDigitalFileDepartInfo();


    /**
     * 根据部门id获取部门信息
     *
     * @param id    部门id
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @return 部门信息集合
     */
    @GET("zftal-mobile/webservice/newmobile/MobileLoginXMLService/digitalArchivesPersonnelList")
    Observable<Response<ResponseListInfo<DigitalFileItemInfo>>> getDigitalFileItemInfo(@Query("dpId")
                                                                                               String id, @Query("start") int start, @Query("size") int size);


    /**
     * 获取收藏列表信息
     *
     * @param params 传递参数
     * @return 收藏信息
     */
    @GET("zftal-mobile/appCenter/appCenter_getFavouritesListByUser.html")
    Observable<Response<ResponseListInfo<FavouritesListInfo>>> getFavouritesList(@QueryMap Map<String, String> params);


    /**
     * 删除一条收藏数据
     *
     * @param memoFileNameList
     * @return
     */
    @GET("zftal-mobile/appCenter/appCenter_deleteFavouriteById.html")
    Observable<Response<String>> deleteFavourites(@Query("favourid") String memoFileNameList);

    /**
     * 签到
     *
     * @param source    签到所加积分
     * @param appsource 签到来源
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_signinAndGetSource.html")
    Observable<Response<String>> signIn(@Query("source") String source, @Query("appsource") String appsource);

    /**
     * 获取积分商城商品列表
     *
     * @param params
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getSourceGoodsList.html")
    Observable<Response<ResponseListInfo<IntegralMallGoodsInfo>>> getIntegralGoodsList(@QueryMap Map<String, String> params);

    /**
     * 兑换商品接口
     *
     * @param params
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_purchaseGoods.html")
    Observable<Response<String>> exchangeGoods(@QueryMap Map<String, String> params);

    /**
     * 获取积分明细记录
     *
     * @param params
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getSourceIncomeHis.html")
    Observable<Response<ResponseListInfo<IntegralIncomeItemInfo>>> getIntegralIncomeList(@QueryMap Map<String, String> params);

    /**
     * 获取商品兑换列表
     *
     * @param params
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getSourceConsumerHis.html")
    Observable<Response<ResponseListInfo<ExchangeRecordItemInfo>>> getExchangeRecordList(@QueryMap Map<String, String> params);

    /**
     * 获取兑换记录详情中商品信息
     *
     * @param goodsID
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_findByGoodsId.html")
    Observable<Response<IntegralMallGoodsInfo>> getExchangeRecordDetail(@Query("goodsid") String goodsID);


    /**
     * 门户认证
     *
     * @param url 动态的url
     * @return string
     */
    @GET
    Observable<Response<String>> portalCertification(@Url String url);

    /**
     * 积分排名
     *
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_getSourcerankingList.html")
    Observable<Response<IntegralRankingInfo>> getIntegralRankingInfo();

    /**
     * 获取食堂列表
     *
     * @param params 参数
     * @return 食堂列表
     */

    @GET("zftal-mobile/commonHttp/commonHttp_getCanteenList.html")
    Observable<Response<ResponseListInfo<EateryInfo>>> getSchoolEateryInfo(@QueryMap Map<String, String> params);

    /**
     * 获取
     *
     * @param params 参数
     * @return 地址列表
     */
    @GET("zftal-mobile/commonHttp/commonHttp_getAddressListByUser.html")
    Observable<Response<ResponseListInfo<UserAddressInfo>>> getUserAddress(@QueryMap Map<String, String> params);

    /**
     * 获取订单列表
     *
     * @param start 第start页
     * @param size  每页要获取的条数
     * @return 订单列表
     */
    @GET("zftal-mobile/commonHttp/commonHttp_getOrderlistByUser.html")
    Observable<Response<ResponseListInfo<OrderForminfo>>> getOrderFormList(@Query("start") String start, @Query("size") String size);

    /**
     * 提交新地址
     *
     * @param params 参数
     * @return 是否成功
     */
    @GET("zftal-mobile/commonHttp/commonHttp_submitAddressForUser.html")
    Observable<Response<String>> submitAddress(@QueryMap Map<String, String> params);

    /**
     * 删除地址
     *
     * @param params 参数
     * @return 是否成功
     */
    @GET("zftal-mobile/commonHttp/commonHttp_deleteAddressByUser.html")
    Observable<Response<String>> deleteAddress(@QueryMap Map<String, String> params);

    /**
     * 提交新地址
     *
     * @param params 参数
     * @return
     */
    @GET("zftal-mobile/commonHttp/commonHttp_submitAddressForUser.html")
    Observable<Response<String>> editeAddress(@QueryMap Map<String, String> params);

    /**
     * 食堂获取详细信息
     *
     * @param canteenid 食堂id
     * @return 所有食物类别以及信息
     */
    @GET("zftal-mobile/commonHttp/commonHttp_getCanteenDetailList.html")
    Observable<Response<List<FoodCataListInfo>>> getFoodInfo(@Query("canteenid") String canteenid);

    /**
     * 我的消息列表
     *
     * @param params
     * @return
     */
    @GET("zftal-mobile/myPortalHttp/myPortalHttp_PushMsgList.html")
    Observable<Response<ResponseListInfo<MyMessageItemInfo>>> getMyMessageList(@QueryMap Map<String, String> params);


    @GET("zftal-mobile/commonHttp/commonHttp_placeOrder.html")
    Observable<Response<String>>submitOrder(@Query("data")String data);
}
