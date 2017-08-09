package com.zfsoftmh.listener;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 自定义回调接口
 */
public interface CallBackListener<T> {

    /**
     * 成功的回调
     *
     * @param data 业务数据
     */
    void onSuccess(T data);

    /**
     * 失败的回调
     *
     * @param errorMsg 显示的错误信息
     */
    void onError(String errorMsg);
}
