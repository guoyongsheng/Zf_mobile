package com.zfsoftmh.ui.base;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 所有的view层都要实现这个接口
 */
public interface BaseView<T> {

    /**
     * 让view层持有presenter层的引用
     *
     * @param presenter 所有的presenter都要继承BasePresenter
     */
    void setPresenter(T presenter);

    /**
     * 判断Fragment是否依附在Activity上
     *
     * @return true:依附在Activity上  false:没有依附在Activity上
     */
    boolean isActive();

    /**
     * 显示对话框
     *
     * @param msg 对话框要显示的信息
     */
    void showProgressDialog(String msg);

    /**
     * 隐藏对话框
     */
    void hideProgressDialog();
}
