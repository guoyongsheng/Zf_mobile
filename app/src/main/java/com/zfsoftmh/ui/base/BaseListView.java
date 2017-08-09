package com.zfsoftmh.ui.base;

import com.zfsoftmh.entity.ResponseListInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-8
 * @Description: 所有有滚动加载的情况协议接口都继承这个接口
 */

public interface BaseListView<T, K> extends BaseView<T> {

    /**
     * 数据获取成功
     *
     * @param info 失物招领的信息
     */
    void loadSuccess(ResponseListInfo<K> info);

    /**
     * 数据获取失败
     *
     * @param errorMsg 失败的信息
     */
    void loadFailure(String errorMsg);

    /**
     * 判断数据是否为空
     *
     * @param list 数据集合
     * @return true :为空 false: 不为空
     */
    boolean checkDataIsNull(List<K> list);
}
