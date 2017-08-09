package com.zfsoftmh.ui.base;

/**
 * @author wesley
 * @date: 2017-8-3
 * @Description:
 */

public interface BaseSingleView<T> extends BaseView<T> {

    /**
     * 数据获取失败
     *
     * @param errorMsg 失败的信息
     */
    void loadError(String errorMsg);
}
