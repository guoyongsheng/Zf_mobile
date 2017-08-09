package com.zfsoftmh.service;

import com.zfsoftmh.entity.ReaderInformationInfo;
import com.zfsoftmh.entity.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 移动学习模块的所有接口---移动学习模块包括: 图书馆、知识文库、教学视频、在线测试、知识交流
 */

public interface MobileLearningApi {

    /**
     * 获取读者信息
     *
     * @return 读者信息
     */
    @GET("zftal-mobile/webservice/newmobile/MobileHWWebService/getreader")
    Observable<Response<List<ReaderInformationInfo>>> getReaderInfo();



}
