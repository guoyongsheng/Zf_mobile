package com.zfsoftmh.service;

import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.entity.CloudNoteInfo;
import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.entity.MeetingManagementInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.entity.QuestionnairePublishedInfo;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.entity.ResponseListInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 办公事务模板所有接口---办公事务包括: 代办事宜、日程、公文、通知、请示
 */

public interface OfficeAffairsApi {

    /**
     * 获取待办事宜列表
     *
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @param sign  加密
     * @return 待办事宜的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/getTodoTaskList")
    Observable<Response<AgencyMattersInfo>> getAgencyMattersData(@Query("start") String start, @Query("size") String size, @Query("sign") String sign);


    /**
     * 获取已办事宜列表
     *
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @return 已办事宜的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/getDoneTaskList")
    Observable<Response<List<AgencyMattersInfo>>> getHasBeenDoneMattersData(@Query("start") String start, @Query("size") String size);


    /**
     * 获取办结事宜列表
     *
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @return 办结事宜的集合
     */
    @GET("zftal-mobile/newmobile/MobileLoginServlet/getSoluteTaskList")
    Observable<Response<List<AgencyMattersInfo>>> getSettlementMattersData(@Query("start") String start, @Query("size") String size);


    /**
     * 上传云笔记图片
     *
     * @param file 图片路径
     * @return Response<String>对象
     */
    @Multipart
    @POST("zftal-mobile/appCenter/appCenter_uploadMemoPicture.html")
    Observable<Response<String>> upLoadCloudNotePic(@Part("apptoken") RequestBody app_token, @Part MultipartBody.Part file);


    /**
     * 上传云笔记
     *
     * @param file        头像路径
     * @param requestBody 额外参数
     * @return Response<String>对象
     */
    @Multipart
    @POST("zftal-mobile/appCenter/appCenter_uploadMemo.html")
    Observable<Response<String>> upLoadCloudNote(@PartMap Map<String, RequestBody> requestBody, @Part MultipartBody.Part file);

    /**
     * 下载文件
     *
     * @param url 动态的url
     * @return 文件
     */
    @Streaming
    @GET
    Observable<retrofit2.Response<ResponseBody>> downLoadFile(@Url String url);

    /**
     * 获取云笔记标签列表
     *
     * @return NoteLabelItemInfo集合
     */
    @GET("zftal-mobile/appCenter/appCenter_getMemoCatalogList.html")
    Observable<Response<List<NoteLabelItemInfo>>> getNoteLabelData();

    /**
     * 获取云笔记列表
     *
     * @param params 参数
     * @return CloudNoteInfo信息
     */
    @FormUrlEncoded
    @POST("zftal-mobile/appCenter/appCenter_getMyMemoList.html")
    Observable<Response<CloudNoteInfo>> getCloudNoteList(@FieldMap Map<String, String> params);

    /**
     * 删除云笔记
     *
     * @param memoFileNameList 参数
     * @return 是否删除成功信息
     */
    @GET("zftal-mobile/appCenter/appCenter_deleteMyMemoList.html")
    Observable<Response<String>> deleteNote(@Query("memoFileNameList") String memoFileNameList);

    /**
     * 提交云笔记标签修改数据
     *
     * @param memoCatalogNameList  参数
     * @param memoCatalogColorList 参数
     * @return NoteLabelItemInfo集合
     */
    @GET("zftal-mobile/appCenter/appCenter_submitMemoCatalogList.html")
    Observable<Response<List<NoteLabelItemInfo>>> submitNoteLabel(
            @Query("memoCatalogNameList") String memoCatalogNameList,
            @Query("memoCatalogColorList") String memoCatalogColorList);


    /**
     * 分页获取失物招领列表
     *
     * @param start  起始页
     * @param size   每页获取多少条数据
     * @param isover 0：未招领 1: 已招领
     * @param title  标题
     * @param userId 用户id
     * @return ResponseResponseListInfoLostAndFoundItemInfo 对象
     */
    @GET("zftal-mobile/appCenter/appCenter_getLossObjectList.html")
    Observable<Response<ResponseListInfo<LostAndFoundItemInfo>>> getLostAndFoundListInfo(@Query("start")
            String start, @Query("size") String size, @Query("isover") String isover,
            @Query("title") String title, @Query("username") String userId, @Query("apptoken") String apptoken);


    /**
     * 提交失物招领
     *
     * @param map  其他参数
     * @param file 文件
     * @return String
     */
    @Multipart
    @POST("zftal-mobile/appCenter/appCenter_publishLoss.html")
    Observable<Response<String>> submitLostAndFound(@PartMap Map<String, RequestBody> map, @PartMap Map<String, RequestBody> file);

    /**
     * 提交问卷调查
     *
     * @param value 问卷调查的信息
     * @return Response<String>
     */
    @FormUrlEncoded
    @POST("zftal-mobile/appCenter/appCenter_submitExam.html")
    Observable<Response<String>> submitQuestionnaire(@Field("xml") String value);

    /**
     * 分页获取问卷调查的信息
     *
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @return Response<QuestionnaireInfo>信息
     */
    @GET("zftal-mobile/appCenter/appCenter_getExamList.html")
    Observable<Response<ResponseListInfo<QuestionnairePublishedInfo>>> getQuestionnaireInfo(@Query("start")
            int start, @Query("size") int size);


    /**
     * 参与问卷调查
     *
     * @param answer 问卷调查的答案
     * @return string
     */
    @FormUrlEncoded
    @POST("zftal-mobile/appCenter/appCenter_submitExamAnswer.html")
    Observable<Response<String>> joinQuestionnaire(@Field("xml") String answer);


    /**
     * 分页获取会议管理的信息
     *
     * @param start 起始页
     * @param size  每页获取多少条数据
     * @param type  类型 1: 我的会议 2: 全体会议
     * @return ResponseResponseListInfoMeetingManagementInfo信息
     */
    @GET("zftal-mobile/webservice/oa/EmailInformationXMLService/getNewConferenceList")
    Observable<Response<ResponseListInfo<MeetingManagementInfo>>> getMeetingManagementInfo(@Query("start")
            int start, @Query("size") int size, @Query("type") int type);



}